package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class LessonDetailController {

    private final BaiHocRepository baiHocRepository;
    private final BaiKiemTraRepository baiKiemTraRepository;
    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    private final LearningProgressService progressService;

    public LessonDetailController(
            BaiHocRepository baiHocRepository,
            BaiKiemTraRepository baiKiemTraRepository,
            IKhoaHocService khoaHocService,
            IDoiTuongSuDungService doiTuongSuDungService,
            DangKyKhoaHocRepository dangKyKhoaHocRepository,
            LearningProgressService progressService
    ) {
        this.baiHocRepository = baiHocRepository;
        this.baiKiemTraRepository = baiKiemTraRepository;
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
        this.progressService = progressService;
    }

    @GetMapping("/lesson/{idBaiHoc}")
    public ModelAndView lessonDetail(@PathVariable String idBaiHoc, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("lesson_detail");

        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null) {
            mv.setViewName("redirect:/courses");
            return mv;
        }

        KhoaHoc course = lesson.getKhoaHoc() != null
                ? khoaHocService.findById(lesson.getKhoaHoc().getIdKhoaHoc())
                : null;
        if (course == null) {
            mv.setViewName("redirect:/courses");
            return mv;
        }

        // auth
        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        mv.addObject("loggedIn", loggedIn);

        DoiTuongSuDung user = null;
        DangKyKhoaHoc dk = null;
        boolean enrolled = false;

        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            mv.addObject("user", user);

            dk = dangKyKhoaHocRepository
                    .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            course.getIdKhoaHoc()
                    )
                    .orElse(null);

            enrolled = (dk != null);
        }

        mv.addObject("course", course);
        mv.addObject("lesson", lesson);
        mv.addObject("enrolled", enrolled);
        mv.addObject("trialMode", !enrolled);

        // prev/next
        BaiHoc next = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocAndThuTuGreaterThanOrderByThuTuAsc(
                course.getIdKhoaHoc(), lesson.getThuTu());
        BaiHoc prev = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocAndThuTuLessThanOrderByThuTuDesc(
                course.getIdKhoaHoc(), lesson.getThuTu());

        // nếu trial thì chỉ allow next trong 4 bài đầu
        if (!enrolled && next != null && next.getThuTu() > 4) next = null;

        mv.addObject("nextLesson", next);
        mv.addObject("prevLesson", prev);

        // progress (chỉ enrolled)
        TienDoHoc tienDo = null;
        if (enrolled && user != null) {
            tienDo = progressService.ensureProgress(user, course);
        }
        mv.addObject("tienDo", tienDo);

        // quiz data (loai=1,2,3 - có thể có quiz)
        if (lesson.getLoai() != 4) {
            BaiKiemTra quiz = baiKiemTraRepository.findByBaiHoc(lesson)
                    .orElseThrow(() ->
                            new IllegalStateException("Quiz not found for lesson " + lesson.getIdBaiHoc())
                    );
            mv.addObject("quiz", quiz);
        }
        return mv;
    }

    // ====== COMPLETE THEORY / VIDEO (enrolled only) ======
    @PostMapping("/lesson/{idBaiHoc}/complete")
    public String completeLesson(@PathVariable String idBaiHoc, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/lesson/" + idBaiHoc;

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null) return "redirect:/courses";

        KhoaHoc course = khoaHocService.findById(lesson.getKhoaHoc().getIdKhoaHoc());
        DangKyKhoaHoc dk = dangKyKhoaHocRepository
                .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                        user.getIdDoiTuong(),
                        course.getIdKhoaHoc()
                )
                .orElse(null);
        if (dk == null) return "redirect:/lesson/" + idBaiHoc; // trial không lưu

        TienDoHoc td = progressService.ensureProgress(user, course);
        BaiHoc next = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocAndThuTuGreaterThanOrderByThuTuAsc(
                course.getIdKhoaHoc(), lesson.getThuTu());

        progressService.markCompletedAndMoveNext(td, lesson, next);
        return "redirect:/lesson/" + idBaiHoc;
    }

    // ====== QUIZ SUBMIT (trial allowed, enrolled saves when pass) ======
    public static class QuizSubmitRequest {
        public Map<Integer, Integer> answers = new HashMap<>(); // questionIndex -> optionIndex
    }

    @PostMapping("/lesson/{idBaiHoc}/quiz/submit")
    @ResponseBody
    public ResponseEntity<?> submitQuiz(@PathVariable String idBaiHoc,
                                        @RequestBody QuizSubmitRequest req,
                                        HttpServletRequest request) {

        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null || lesson.getLoai() != 2) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Invalid lesson"));
        }

        KhoaHoc course = khoaHocService.findById(lesson.getKhoaHoc().getIdKhoaHoc());
        BaiKiemTra quiz = baiKiemTraRepository.findByBaiHoc(lesson)
                .orElseThrow(() ->
                        new IllegalStateException("Quiz not found for lesson " + lesson.getIdBaiHoc())
                );
        if (quiz == null) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Quiz not found"));
        }

        // auth & enrolled?
        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        DoiTuongSuDung user = null;
        boolean enrolled = false;

        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            DangKyKhoaHoc dk = dangKyKhoaHocRepository
                    .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            course.getIdKhoaHoc()
                    )
                    .orElse(null);
            enrolled = (dk != null);
        }

        boolean trialAllowed = (!enrolled && lesson.getThuTu() <= 4);
        if (!enrolled && !trialAllowed) {
            return ResponseEntity.status(403).body(Map.of("ok", false, "message", "Locked lesson"));
        }

        int total = quiz.getCauHoi().size();
        int correct = 0;

        List<Map<String, Object>> details = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            CauHoiTracNghiem q = quiz.getCauHoi().get(i);
            if (q.getDapAnDungIndex() == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("ok", false, "message", "Quiz data is invalid (missing correct answer)")
                );
            }
            int correctIndex = q.getDapAnDungIndex();
            Integer picked = req.answers.get(i);

            boolean isCorrect = (picked != null && picked == correctIndex);
            if (isCorrect) correct++;

            // build per-choice explanation
            List<Map<String, Object>> choiceExplain = new ArrayList<>();
            for (int c = 0; c < q.getLuaChon().size(); c++) {
                String reasonWrong = (q.getGiaiThichSai() != null && q.getGiaiThichSai().size() > c)
                        ? q.getGiaiThichSai().get(c)
                        : null;

                // default fallback
                if (reasonWrong == null && c != correctIndex) {
                    reasonWrong = "This choice does not match the concept asked in the question.";
                }

                choiceExplain.add(Map.of(
                        "index", c,
                        "text", q.getLuaChon().get(c),
                        "isCorrect", c == correctIndex,
                        "reason", (c == correctIndex)
                                ? (q.getGiaiThichDung() != null ? q.getGiaiThichDung()
                                : "This is the correct answer based on the C++ standard behavior / definition.")
                                : reasonWrong
                ));
            }

            details.add(Map.of(
                    "questionIndex", i,
                    "question", q.getNoiDung(),
                    "picked", picked,
                    "correctIndex", correctIndex,
                    "isCorrect", isCorrect,
                    "choices", choiceExplain
            ));
        }

        double score = total == 0 ? 0 : (correct * 100.0 / total);
        boolean passed = score >= 70.0;

        // save progress if enrolled & passed
        if (enrolled && passed && user != null) {
            TienDoHoc td = progressService.ensureProgress(user, course);
            BaiHoc next = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocAndThuTuGreaterThanOrderByThuTuAsc(
                    course.getIdKhoaHoc(), lesson.getThuTu());
            progressService.markCompletedAndMoveNext(td, lesson, next);
        }

        return ResponseEntity.ok(Map.of(
                "ok", true,
                "total", total,
                "correct", correct,
                "score", score,
                "passed", passed,
                "saved", (enrolled && passed),
                "details", details
        ));
    }

    // ====== CODE SUBMIT (trial allowed; enrolled saves when pass) ======
    public static class CodeSubmitRequest {
        public String userOutput;
    }

    @PostMapping("/lesson/{idBaiHoc}/code/submit")
    @ResponseBody
    public ResponseEntity<?> submitCode(@PathVariable String idBaiHoc,
                                        @RequestBody CodeSubmitRequest req,
                                        HttpServletRequest request) {

        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null || lesson.getLoai() != 4) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Invalid lesson"));
        }

        KhoaHoc course = khoaHocService.findById(lesson.getKhoaHoc().getIdKhoaHoc());

        String expected = lesson.getCodeOutputMau();
        if (expected == null) expected = "";

        String userOut = req.userOutput == null ? "" : req.userOutput;

        String normExpected = normalize(expected);
        String normUser = normalize(userOut);

        boolean passed = !normExpected.isBlank() && normUser.contains(normExpected);

        // auth & enrolled?
        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        DoiTuongSuDung user = null;
        boolean enrolled = false;

        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            DangKyKhoaHoc dk = dangKyKhoaHocRepository
                    .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            course.getIdKhoaHoc()
                    )
                    .orElse(null);
            enrolled = (dk != null);
        }

        boolean trialAllowed = (!enrolled && lesson.getThuTu() <= 4);
        if (!enrolled && !trialAllowed) {
            return ResponseEntity.status(403).body(Map.of("ok", false, "message", "Locked lesson"));
        }

        boolean saved = false;
        if (enrolled && passed && user != null) {
            TienDoHoc td = progressService.ensureProgress(user, course);
            BaiHoc next = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocAndThuTuGreaterThanOrderByThuTuAsc(
                    course.getIdKhoaHoc(), lesson.getThuTu());
            progressService.markCompletedAndMoveNext(td, lesson, next);
            saved = true;
        }

        return ResponseEntity.ok(Map.of(
                "ok", true,
                "passed", passed,
                "saved", saved,
                "message", passed
                        ? "Accepted! Your output matches the expected output."
                        : "Not accepted. Make sure your output contains the expected result (formatting matters)."
        ));
    }

    private static String normalize(String s) {
        // normalize line endings + trim + collapse multiple spaces
        return s.replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim()
                .replaceAll("[ \\t]+", " ");
    }
}

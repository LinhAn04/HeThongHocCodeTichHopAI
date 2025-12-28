package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Quản lý CRUD bài học
@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {

    private final BaiHocRepository lessonRepo;
    private final KhoaHocRepository courseRepo;
    private final BaiKiemTraRepository quizRepo;
    private final AdminCourseService courseService;

    public AdminLessonController(
            BaiHocRepository lessonRepo,
            KhoaHocRepository courseRepo,
            BaiKiemTraRepository quizRepo,
            AdminCourseService courseService) {
        this.lessonRepo = lessonRepo;
        this.courseRepo = courseRepo;
        this.quizRepo = quizRepo;
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(Model model, HttpSession session) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<KhoaHoc> activeCourses =
                courseService.findAllActive();

        model.addAttribute("user", user);
        model.addAttribute("courses", activeCourses);
        model.addAttribute("activeMenu", "lessons");

        return "admin/lessons_management/admin_lessons";
    }

    @GetMapping("/{courseId}")
    public String listLessons(
            @PathVariable String courseId,
            Model model, HttpSession session) {

        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();

        List<BaiHoc> lessons = lessonRepo.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(courseId);

        int afterThuTu;
        if (lessons.isEmpty()) {
            afterThuTu = -1;
        } else {
            afterThuTu = lessons.get(lessons.size() - 1).getThuTu();
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("afterThuTu", afterThuTu);
        model.addAttribute("isEdit", false);
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        return "admin/lessons_management/admin_lessons";
    }

    @GetMapping("/new/{courseId}")
    public String create(@PathVariable String courseId,
                         Model model,
                         HttpSession session) {

        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();

        BaiHoc lesson = new BaiHoc();
        lesson.setLoai(1);

        QuizForm quizForm = new QuizForm();

        LessonForm form = new LessonForm();
        form.setLesson(lesson);
        form.setQuizForm(quizForm);

        List<BaiHoc> lessons = lessonRepo.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(courseId);

        int afterThuTu = lessons.isEmpty() ? -1 : lessons.get(lessons.size() - 1).getThuTu();

        model.addAttribute("form", form);
        model.addAttribute("lessons", lessons);
        model.addAttribute("afterThuTu", afterThuTu);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isEdit", false);
        model.addAttribute("user", user);

        return "admin/lessons_management/admin_lesson_form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id,
                           Model model,
                           HttpSession session) {

        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();

        QuizForm quizForm = new QuizForm();
        List<CauHoiTracNghiem> questions = new ArrayList<>();

        quizRepo.findByBaiHoc(lesson).ifPresent(quiz -> {

            if (quiz.getCauHoi() != null) {
                questions.addAll(quiz.getCauHoi());
            }
        });

        for (CauHoiTracNghiem q : questions) {
            if (q.getLuaChon() == null)
                q.setLuaChon(new ArrayList<>());

            while (q.getLuaChon().size() < 4)
                q.getLuaChon().add("");

            if (q.getGiaiThichSai() == null)
                q.setGiaiThichSai(new ArrayList<>());

            while (q.getGiaiThichSai().size() < 4)
                q.getGiaiThichSai().add("");
        }

        quizForm.setQuizCauHoi(questions);

        if (quizForm.getQuizCauHoi() == null) {
            quizForm.setQuizCauHoi(new ArrayList<>());
        }

        LessonForm form = new LessonForm();
        form.setLesson(lesson);
        form.setQuizForm(quizForm);

        List<BaiHoc> lessons = lessonRepo.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(courseId);

        int afterThuTu = lesson.getThuTu() == 1 ? -1 : lesson.getThuTu() - 1;

        model.addAttribute("form", form);
        model.addAttribute("lessons", lessons);
        model.addAttribute("afterThuTu", afterThuTu);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isEdit", true);
        model.addAttribute("user", user);

        return "admin/lessons_management/admin_lesson_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("form") LessonForm form,
                       @RequestParam String courseId,
                       @RequestParam Integer afterThuTu,
                       @RequestParam(required = false) Integer oldThuTu,
                       @RequestParam(required = false) Boolean oldActive,
                       HttpSession session) {

        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        BaiHoc lesson = form.getLesson();
        QuizForm quizForm = form.getQuizForm();

        if (quizForm == null) {
            quizForm = new QuizForm();
            quizForm.setQuizCauHoi(new ArrayList<>());
        }

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();
        lesson.setKhoaHoc(course);

        int newThuTu = afterThuTu < 0 ? 1 : afterThuTu + 1;
        boolean isEdit = (oldThuTu != null);

        if (!isEdit) {
            lessonRepo.increaseThuTuFrom(courseId, newThuTu);
            lesson.setThuTu(newThuTu);
            lesson.setIsActive(true);
        } else {
            lesson.setIsActive(oldActive);
            if (!Objects.equals(oldThuTu, newThuTu)) {
                lessonRepo.decreaseThuTuFrom(courseId, oldThuTu + 1);
                lessonRepo.increaseThuTuFrom(courseId, newThuTu);
                lesson.setThuTu(newThuTu);
            } else {
                lesson.setThuTu(oldThuTu);
            }
        }

        BaiHoc saved = lessonRepo.save(lesson);

        if (lesson.getLoai() == 2 &&
                (quizForm.getQuizCauHoi() == null || quizForm.getQuizCauHoi().isEmpty())) {

            quizRepo.findByBaiHoc(saved).ifPresent(quizRepo::delete);
        }

        if (lesson.getLoai() == 2 &&
                quizForm.getQuizCauHoi() != null &&
                !quizForm.getQuizCauHoi().isEmpty()) {

            BaiKiemTra quiz = quizRepo.findByBaiHoc(saved)
                    .orElseGet(BaiKiemTra::new);

            quiz.setBaiHoc(saved);

            quizForm.getQuizCauHoi().forEach(q -> {
                Integer correct = q.getDapAnDungIndex();
                if (correct != null && q.getGiaiThichSai() != null &&
                        q.getGiaiThichSai().size() > correct) {
                    q.getGiaiThichSai().set(correct, null);
                }
            });

            quiz.setCauHoi(quizForm.getQuizCauHoi());
            quizRepo.save(quiz);
        }

        return "redirect:/admin/lessons/" + courseId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id,
                         Model model, HttpSession session) {

        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();
        Integer thuTu = lesson.getThuTu();

        lessonRepo.deleteById(id);
        lessonRepo.decreaseThuTuFrom(courseId, thuTu + 1);

        model.addAttribute("user", user);
        return "redirect:/admin/lessons/" + courseId;
    }

    @PostMapping("/hide/{id}")
    public String hide(@PathVariable String id) {

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        lesson.setIsActive(false);
        lessonRepo.save(lesson);

        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();
        Integer thuTu = lesson.getThuTu();
        lessonRepo.decreaseThuTuFrom(courseId, thuTu + 1);

        return "redirect:/admin/lessons/" + lesson.getKhoaHoc().getIdKhoaHoc();
    }

    @PostMapping("/unhide/{id}")
    public String unhide(@PathVariable String id) {

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        lesson.setIsActive(true);
        lessonRepo.save(lesson);

        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();
        Integer thuTu = lesson.getThuTu();
        lessonRepo.increaseThuTuFrom(courseId, thuTu + 1);

        return "redirect:/admin/lessons/" + lesson.getKhoaHoc().getIdKhoaHoc();
    }
}

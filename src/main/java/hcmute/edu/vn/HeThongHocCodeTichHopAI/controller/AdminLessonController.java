package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();

        if (!Boolean.TRUE.equals(course.getIsActive())) {
            return "redirect:/admin/lessons";
        }

        BaiHoc lesson = new BaiHoc();
        lesson.setLoai(1);

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
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isEdit", false);

        return "admin/lessons_management/admin_lesson_form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable String id,
            Model model, HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();

        List<BaiHoc> lessons =
                lessonRepo.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(courseId);

        int afterThuTu;
        if (lesson.getThuTu() == 1) {
            afterThuTu = -1;
        } else {
            afterThuTu = lesson.getThuTu() - 1;
        }

        model.addAttribute("user", user);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", courseId);
        model.addAttribute("lessons", lessons);
        model.addAttribute("afterThuTu", afterThuTu);
        model.addAttribute("isEdit", true);

        return "admin/lessons_management/admin_lesson_form";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute BaiHoc lesson,
            @RequestParam String courseId,
            @RequestParam Integer afterThuTu,
            @RequestParam(required = false) Integer oldThuTu,
            @RequestParam(required = false) Boolean oldActive,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();
        lesson.setKhoaHoc(course);

        int newThuTu;
        if (afterThuTu < 0) {
            newThuTu = 1;
        } else {
            newThuTu = afterThuTu + 1;
        }

        boolean isEdit = (oldThuTu != null);

        if (!isEdit) {
            // CREATE
            lessonRepo.increaseThuTuFrom(courseId, newThuTu);
            lesson.setThuTu(newThuTu);
            lesson.setIsActive(true);
        } else {
            // EDIT
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

        // QUIZ
        if (lesson.getLoai() == 2) {
            if (!quizRepo.existsByBaiHoc(saved)) {
                BaiKiemTra quiz = new BaiKiemTra();
                quiz.setBaiHoc(saved);
                quizRepo.save(quiz);
            }
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

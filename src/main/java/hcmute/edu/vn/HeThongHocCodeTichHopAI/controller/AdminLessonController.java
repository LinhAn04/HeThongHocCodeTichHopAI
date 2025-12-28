package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Quản lý CRUD bài học
@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {

    private final BaiHocRepository lessonRepo;
    private final KhoaHocRepository courseRepo;
    private final AdminCourseService courseService;

    public AdminLessonController(BaiHocRepository lessonRepo,
                                 KhoaHocRepository courseRepo,
                                 AdminCourseService courseService) {
        this.lessonRepo = lessonRepo;
        this.courseRepo = courseRepo;
        this.courseService = courseService;
    }

//    @GetMapping("/{courseId}")
//    public String list(@PathVariable String courseId, Model model) {
//
//        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();
//
//        model.addAttribute("course", course);
//        model.addAttribute("lessons",
//                lessonRepo.findByKhoaHoc_IdKhoaHoc(courseId));
//
//        model.addAttribute("locked",
//                courseService.hasEnrollment(courseId));
//
//        return "admin/courses_management/admin_lessons";
//    }

    @GetMapping("/new/{courseId}")
    public String create(@PathVariable String courseId, Model model) {

        model.addAttribute("lesson", new BaiHoc());
        model.addAttribute("courseId", courseId);
        model.addAttribute("isEdit", false);
        return "admin/courses_management/admin_lesson_form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        boolean locked =
                courseService.hasEnrollment(
                        lesson.getKhoaHoc().getIdKhoaHoc());

        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId",
                lesson.getKhoaHoc().getIdKhoaHoc());
        model.addAttribute("locked", locked);
        model.addAttribute("isEdit", true);

        return "admin/courses_management/admin_lesson_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BaiHoc lesson,
                       @RequestParam String courseId) {

        KhoaHoc course = courseRepo.findById(courseId).orElseThrow();
        lesson.setKhoaHoc(course);

        lessonRepo.save(lesson);
        return "redirect:/admin/courses/edit/" + courseId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {

        BaiHoc lesson = lessonRepo.findById(id).orElseThrow();
        String courseId = lesson.getKhoaHoc().getIdKhoaHoc();

        if (!courseService.hasEnrollment(courseId)) {
            lessonRepo.deleteById(id);
        }
        return "redirect:/admin/courses/edit/" + courseId;
    }
}

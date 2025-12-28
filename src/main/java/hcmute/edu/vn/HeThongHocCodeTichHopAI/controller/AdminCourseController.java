package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// Quản lý CRUD khóa học
@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final AdminCourseService courseService;

    public AdminCourseController(AdminCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        List<KhoaHoc> courses = courseService.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute(
                "canDeleteMap",
                courseService.buildCanDeleteMap(courses)
        );

        model.addAttribute("activeMenu", "courses");
        return "admin/courses_management/admin_courses";
    }

    @GetMapping("/new")
    public String create(Model model, HttpSession session) {
        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("course", new KhoaHoc());
        model.addAttribute("lessons", List.of());
        model.addAttribute("locked", false);
        model.addAttribute("isEdit", false);

        return "admin/courses_management/admin_course_form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model, HttpSession session) {
        DoiTuongSuDung user = (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        KhoaHoc course = courseService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("course", course);

        model.addAttribute("lessons",
                courseService.findLessonsByCourse(id));

        model.addAttribute("locked",
                courseService.hasEnrollment(id));

        model.addAttribute("isEdit", true);

        return "admin/courses_management/admin_course_form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute KhoaHoc course) {
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    @PostMapping("/upload-cover")
    @ResponseBody
    public Map<String, Object> uploadCourseCover(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) {
            return Map.of(
                    "success", false,
                    "message", "Unauthorized"
            );
        }

        try {
            String uploadDir = "uploads/admin/courses/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return Map.of(
                    "success", true,
                    "path", "/uploads/admin/courses/" + fileName
            );

        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "Upload failed"
            );
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        courseService.deleteOrHide(id);
        return "redirect:/admin/courses";
    }

    @PostMapping("/hide/{id}")
    public String hide(@PathVariable String id) {
        courseService.hide(id);
        return "redirect:/admin/courses";
    }

    @PostMapping("/unhide/{id}")
    public String unhide(@PathVariable String id) {
        courseService.unhide(id);
        return "redirect:/admin/courses";
    }
}

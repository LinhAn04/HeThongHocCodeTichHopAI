package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.AdminCourseService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Quản lý CRUD khóa học
@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {
    private final AdminCourseService service;

    public AdminCourseController(AdminCourseService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("courses", service.findAll());
        model.addAttribute("activeMenu", "courses");
        return "admin/courses_management/courses";
    }

    @GetMapping("/new")
    public String create(Model model, HttpSession session) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("course", new KhoaHoc());
        return "admin/courses_management/course_form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model, HttpSession session) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("course", service.findById(id));
        return "admin/courses_management/course_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute KhoaHoc course, HttpSession session, Model model) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        service.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session, Model model) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        service.delete(id);
        return "redirect:/admin/courses";
    }
}


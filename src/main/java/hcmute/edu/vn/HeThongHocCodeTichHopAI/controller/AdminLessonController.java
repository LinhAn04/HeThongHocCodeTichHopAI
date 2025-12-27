package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.BaiHocRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Quản lý CRUD bài học
@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {
    private final BaiHocRepository repo;

    public AdminLessonController(BaiHocRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{courseId}")
    public String list(@PathVariable String courseId, Model model) {
        model.addAttribute("lessons",
                repo.findByKhoaHoc_IdKhoaHoc(courseId));
        model.addAttribute("courseId", courseId);

        return "admin/courses_management/lessons"; //?
    }

    @GetMapping("/new/{courseId}")
    public String create(@PathVariable String courseId, Model model) {
        BaiHoc b = new BaiHoc();
        model.addAttribute("lesson", b);
        model.addAttribute("courseId", courseId);
        return "admin/courses_management/lesson_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BaiHoc lesson,
                       @RequestParam String courseId) {
        repo.save(lesson);
        return "redirect:/admin/lessons/" + courseId;
    }

    @GetMapping("/delete/{id}/{courseId}")
    public String delete(@PathVariable String id,
                         @PathVariable String courseId) {
        repo.deleteById(id);
        return "redirect:/admin/lessons/" + courseId;
    }
}


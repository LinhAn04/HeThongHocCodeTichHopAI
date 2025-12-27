package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.BaiHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {

    @Autowired
    private BaiHocRepository repo;

    @GetMapping("/{courseId}")
    public String list(@PathVariable String courseId, Model model) {
        model.addAttribute("lessons",
                repo.findByKhoaHoc_IdKhoaHoc(courseId));
        model.addAttribute("courseId", courseId);

        return "admin/lessons";
    }

    @GetMapping("/new/{courseId}")
    public String create(@PathVariable String courseId, Model model) {
        BaiHoc b = new BaiHoc();
        model.addAttribute("lesson", b);
        model.addAttribute("courseId", courseId);
        return "admin/lesson_form";
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


package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        return "redirect:/admin/users";
    }
}


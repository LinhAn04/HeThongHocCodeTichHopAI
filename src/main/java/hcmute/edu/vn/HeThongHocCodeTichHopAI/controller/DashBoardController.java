package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {
    @GetMapping("/")
    public String home() {
        return "redirect:/courses";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/dashboard-admin")
    public String dashboardAdmin() {
        return "redirect:dashboard_admin";
    }

    @GetMapping("/dashboard-customer")
    public String dashboardCustomer() {
        return "redirect:dashboard_customer";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot_password";
    }
}
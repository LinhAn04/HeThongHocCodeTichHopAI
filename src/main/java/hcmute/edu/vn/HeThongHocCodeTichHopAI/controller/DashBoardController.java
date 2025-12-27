package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {
    @GetMapping("/")
    public String home(HttpSession session) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");
        if (user == null) {
            return "redirect:/courses";
        }
        if (user.getLoaiDoiTuongSuDung().name().equals("ADMIN")) {
            return "redirect:/admin/users";
        }
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
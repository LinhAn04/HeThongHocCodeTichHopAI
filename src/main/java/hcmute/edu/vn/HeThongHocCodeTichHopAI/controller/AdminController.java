package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Quản lý tài khoản admin
    @GetMapping("/account-setting")
    public String accountSetting(HttpSession session, Model model,
                                 HttpServletRequest request) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        ModelAndView mv = new ModelAndView();

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);

        mv.addObject("loggedIn", loggedIn);
        model.addAttribute("user", user);
        model.addAttribute("loggedIn", true);
        model.addAttribute("activeMenu", "account");

        return "admin/account_setting/account_setting";
    }

    // Quản lý tài khoản người dùng
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        return "redirect:/admin/users";
    }
}


package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    private final DoiTuongSuDungRepository doiTuongSuDungRepository;

    public AdminAccountController(DoiTuongSuDungRepository doiTuongSuDungRepository){
        this.doiTuongSuDungRepository = doiTuongSuDungRepository;
    }

    @GetMapping("/account-setting")
    public String accountSetting(HttpSession session, Model model) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("loggedIn", true);
        model.addAttribute("activeMenu", "account");

        return "admin/account_setting";
    }
}

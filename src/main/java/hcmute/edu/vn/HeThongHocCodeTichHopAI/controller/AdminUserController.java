package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.AdminUserService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.DoiTuongSuDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService service;
    private final DoiTuongSuDungService doiTuongSuDungService;
    private final TKDoiTuongSuDungRepository taiKhoanRepository;

    public AdminUserController(AdminUserService service,
                               DoiTuongSuDungService doiTuongSuDungService,
                               TKDoiTuongSuDungRepository taiKhoanRepository) {
        this.service = service;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {

        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("users", service.getAllStudents());
        model.addAttribute("activeMenu", "users");

        return "admin/user_management/users";
    }

    @PostMapping("/toggle/{id}")
    public String toggleUserStatus(@PathVariable String id) {

        DoiTuongSuDung user = doiTuongSuDungService.findById(id);
        if (user == null || user.getTaiKhoan() == null) {
            return "redirect:/admin/users";
        }

        TKDoiTuongSuDung tk = user.getTaiKhoan();
        tk.setActive(!tk.isActive());
        taiKhoanRepository.save(tk);

        return "redirect:/admin/users";
    }
}

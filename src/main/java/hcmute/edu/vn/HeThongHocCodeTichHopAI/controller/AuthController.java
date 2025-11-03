package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IAuthService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IEmailService emailService;

    @PostMapping("/register")
    public ModelAndView register(
            @RequestParam String hoTen,
            @RequestParam String soDienThoai,
            @RequestParam String ngaySinh,
            @RequestParam String gioiTinh,
            @RequestParam String email,
            @RequestParam String password
    ) {
        ModelAndView mv = new ModelAndView();

        if (!email.endsWith("@gmail.com")) {
            mv.setViewName("register");
            mv.addObject("error", "Email phải có đuôi @gmail.com");
            return mv;
        }

        try {
            authService.register(hoTen, soDienThoai, ngaySinh, gioiTinh, email, password);
            mv.setViewName("verify");
            mv.addObject("email", email);
            mv.addObject("message", "Đăng ký thành công! Vui lòng kiểm tra email để nhập mã xác thực.");
            return mv;
        } catch (RuntimeException e) {
            mv.setViewName("register");
            mv.addObject("error", e.getMessage());
            return mv;
        }
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        ModelAndView mv = new ModelAndView();

        try {
            TKDoiTuongSuDung tk = authService.login(email, password);

            // Nếu chưa kích hoạt
            if (!tk.isTrangThaiKichHoat()) {
                // Gửi mã xác thực mới
                String code = emailService.createCode(email);
                emailService.sendVerificationCode(email, code);

                mv.setViewName("verify");
                mv.addObject("email", email);
                mv.addObject("message", "Tài khoản chưa kích hoạt. Mã xác thực mới đã được gửi đến email của bạn.");
                return mv;
            }

            if (tk.getDoiTuongSuDung() != null &&
                    tk.getDoiTuongSuDung().getLoaiDoiTuongSuDung() == LoaiDoiTuongSuDung.ADMIN) {
                mv.setViewName("dashboard_admin");
                mv.addObject("role", "ADMIN");
            } else {
                mv.setViewName("dashboard_customer");
                mv.addObject("role", "KHACHHANG");
            }

            return mv;

        } catch (RuntimeException e) {
            mv.setViewName("login");
            mv.addObject("error", e.getMessage());
            return mv;
        }
    }

    @PostMapping("/verify-code")
    public ModelAndView verifyCode(@RequestParam String email, @RequestParam String code) {
        ModelAndView mv = new ModelAndView();

        boolean valid = emailService.verify(email, code);

        if (!valid) {
            mv.setViewName("verify");
            mv.addObject("email", email);
            mv.addObject("error", "Mã xác thực sai hoặc đã hết hạn!");
            return mv;
        }

        authService.activateUser(email);
        mv.setViewName("dashboard_customer");
        mv.addObject("role", "KHACHHANG");
        mv.addObject("message", "Tài khoản đã được kích hoạt thành công!");
        return mv;
    }
}
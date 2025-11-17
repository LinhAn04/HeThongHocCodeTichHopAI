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
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam String password,
            @RequestParam(defaultValue = "STUDENT") String role
    ) {
        ModelAndView mv = new ModelAndView();

        if (!email.endsWith("@gmail.com")) {
            mv.setViewName("register");
            mv.addObject("error",
                    "Email format is incorrect. Please re-enter.");
            return mv;
        }

        try {
            authService.register(fullName, email, password, role);
            mv.setViewName("verify");
            mv.addObject("email", email);
            mv.addObject("message",
                    "Registration successful! Please check your email for verification code.");
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
                mv.addObject("message", "Account not activated. New verification code has been sent to your email.");
                return mv;
            }

            if (tk.getDoiTuongSuDung() != null &&
                    tk.getDoiTuongSuDung().getLoaiDoiTuongSuDung() == LoaiDoiTuongSuDung.ADMIN) {
                mv.setViewName("dashboard_admin");
                mv.addObject("role", "ADMIN");
            } else {
                mv.setViewName("dashboard_customer");
                mv.addObject("role", "STUDENT");
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
            mv.addObject("error", "The verification code is incorrect or has expired!");
            return mv;
        }

        authService.activateUser(email);
        mv.setViewName("dashboard_customer");
        mv.addObject("role", "STUDENT");
        mv.addObject("message", "Account has been activated successfully!");
        return mv;
    }
}
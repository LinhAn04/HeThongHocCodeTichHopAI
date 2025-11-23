package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IAuthService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private TKDoiTuongSuDungRepository tkRepository;

    @PostMapping("/register")
    public ModelAndView register(
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam String password,
            @RequestParam(defaultValue = "STUDENT") String role
    ) {
        ModelAndView mv = new ModelAndView();
        // 1. Check email format
        if (!email.contains("@")) {
            mv.setViewName("register");
            mv.addObject("error", "Invalid email format.");
            return mv;
        }
        // 2. Check email already exists
        if (authService.existsByEmail(email)) {
            mv.setViewName("register");
            mv.addObject("error", "Email already exists!");
            return mv;
        }
        try {
            // 3. Register
            authService.register(fullName, email, password, role);

            mv.setViewName("otp_verify");
            mv.addObject("email", email);
            mv.addObject("mode", "register");
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

                mv.setViewName("otp_verify");
                mv.addObject("email", email);
                mv.addObject("mode", "register");
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

            if (e.getMessage().equals("Account email not verified!")) {
                mv.addObject("unverifiedEmail", email); // Gửi lại email xuống FE
            }
            return mv;
        }
    }

    @PostMapping("/verify-register")
    public ModelAndView verifyCode(@RequestParam String email, @RequestParam String code) {
        ModelAndView mv = new ModelAndView();

        boolean valid = emailService.verify(email, code);

        if (!valid) {
            mv.setViewName("otp_verify");
            mv.addObject("email", email);
            mv.addObject("mode", "register");
            mv.addObject("error", "The verification code is incorrect or has expired!");
            return mv;
        }

        authService.activateUser(email);
        mv.setViewName("login");
        mv.addObject("role", "STUDENT");
        mv.addObject("mode", "register");
        mv.addObject("message", "Account has been activated successfully! Please log in again.");
        return mv;
    }

    @GetMapping("/resend-register")
    public ModelAndView resendVerify(@RequestParam String email) {
        ModelAndView mv = new ModelAndView();

        emailService.resendVerificationCode(email);

        mv.setViewName("otp_verify");
        mv.addObject("email", email);
        mv.addObject("mode", "register");
        mv.addObject("message", "Verification code re-sent to your email.");
        return mv;
    }

    @PostMapping("/forgot-password")
    public ModelAndView sendResetPassword(@RequestParam String email) {
        ModelAndView mv = new ModelAndView();

        if (email == null || email.trim().isEmpty()) {
            mv.setViewName("forgot_password");
            mv.addObject("error", "Email cannot be empty.");
            return mv;
        }

        if (!email.contains("@")) {
            mv.setViewName("forgot_password");
            mv.addObject("error", "Invalid email format.");
            return mv;
        }

        // Lấy user trực tiếp từ repository
        TKDoiTuongSuDung tk = tkRepository.findByTenDangNhap(email).orElse(null);

        if (tk == null) {
            mv.setViewName("forgot_password");
            mv.addObject("error", "Email does not exist.");
            return mv;
        }

        // Kiểm tra tài khoản đã activate chưa
        if (!tk.isTrangThaiKichHoat()) {
            mv.setViewName("forgot_password");
            mv.addObject("unverifiedEmail", email);
            mv.addObject("error",
                    "Your account is not verified yet.");
            return mv;
        }

        // Gửi OTP reset
        String code = emailService.createCode(email);
        emailService.sendResetPasswordEmail(email, code);

        mv.setViewName("otp_verify");
        mv.addObject("mode", "reset");
        mv.addObject("email", email);
        mv.addObject("message",
                "Please check your email for verification code to reset your password.");
        return mv;
    }

    @PostMapping("/verify-reset-pass")
    public ModelAndView verifyResetCode(
            @RequestParam String email,
            @RequestParam String code
    ) {
        ModelAndView mv = new ModelAndView();

        if (code.trim().isEmpty()) {
            mv.setViewName("otp_verify");
            mv.addObject("mode", "reset");
            mv.addObject("email", email);
            mv.addObject("error", "OTP cannot be empty.");
            return mv;
        }

        boolean ok = emailService.verify(email, code);

        if (!ok) {
            mv.setViewName("otp_verify");
            mv.addObject("mode", "reset");
            mv.addObject("email", email);
            mv.addObject("error", "Invalid or expired OTP.");
            return mv;
        }

        mv.setViewName("change_password");
        mv.addObject("mode", "reset");
        mv.addObject("email", email);
        mv.addObject("message", "OTP authentication successful, you can change password now.");
        return mv;
    }

    @GetMapping("/resend-reset-pass")
    public ModelAndView resendReset(@RequestParam String email) {
        ModelAndView mv = new ModelAndView();

        emailService.resendResetPasswordCode(email);

        mv.setViewName("otp_verify");
        mv.addObject("email", email);
        mv.addObject("mode", "reset");
        mv.addObject("message", "New reset OTP has been sent to your email.");
        return mv;
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        ModelAndView mv = new ModelAndView();

        if (!newPassword.equals(confirmPassword)) {
            mv.setViewName("change_password");
            mv.addObject("email", email);
            mv.addObject("error", "Passwords do not match.");
            return mv;
        }

        // Tìm user
        TKDoiTuongSuDung tk = tkRepository.findByTenDangNhap(email)
                .orElse(null);

        if (tk == null) {
            mv.setViewName("change_password");
            mv.addObject("error", "Account not found.");
            return mv;
        }

        // Hash password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        tk.setMatKhau(encoder.encode(newPassword));

        // Update DB
        tkRepository.save(tk);

        mv.setViewName("login");
        mv.addObject("message", "Password updated successfully! Please log in again.");
        return mv;
    }
}
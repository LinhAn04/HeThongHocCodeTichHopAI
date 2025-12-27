package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IAuthService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.IEmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

        if (!email.contains("@")) {
            mv.setViewName("register");
            mv.addObject("error", "Invalid email format.");
            return mv;
        }

        if (authService.existsByEmail(email)) {
            mv.setViewName("register");
            mv.addObject("error", "Email already exists!");
            return mv;
        }

        authService.register(fullName, email, password, role);

        mv.setViewName("otp_verify");
        mv.addObject("email", email);
        mv.addObject("mode", "register");
        mv.addObject("message", "Registration successful! Please check your email for verification code.");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest request,
                              @RequestParam String email,
                              @RequestParam String password) {

        ModelAndView mv = new ModelAndView();

        try {
            TKDoiTuongSuDung tk = authService.login(email, password);

            // Nếu chưa kích hoạt — KHÔNG lưu session
            if (!tk.isTrangThaiKichHoat()) {
                String code = emailService.createCode(email);
                emailService.sendVerificationCode(email, code);

                mv.setViewName("otp_verify");
                mv.addObject("email", email);
                mv.addObject("mode", "register");
                mv.addObject("message", "Account not activated. New verification code has been sent to your email.");
                return mv;
            }

            // Đã kích hoạt → Lưu session
            DoiTuongSuDung user = tk.getDoiTuongSuDung();

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("email", email);
            session.setAttribute("role", user.getLoaiDoiTuongSuDung());

            if (tk.getDoiTuongSuDung().getLoaiDoiTuongSuDung() == LoaiDoiTuongSuDung.ADMIN) {
                return new ModelAndView("redirect:/admin");
            }
             else {
                mv.setViewName("redirect:/courses");
                mv.addObject("role", "STUDENT");
            }
            return mv;

        } catch (RuntimeException e) {
            mv.setViewName("login");
            mv.addObject("error", e.getMessage());

            if (e.getMessage().equals("Account email not verified!")) {
                mv.addObject("unverifiedEmail", email);
            }
            return mv;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/courses";
    }

    @PostMapping("/verify-register")
    public ModelAndView verifyCode(
            HttpServletRequest request,
            @RequestParam String email,
            @RequestParam String code
    ) {
        ModelAndView mv = new ModelAndView();

        try {
            boolean valid = emailService.verify(email, code);

            if (!valid) {
                mv.setViewName("otp_verify");
                mv.addObject("email", email);
                mv.addObject("mode", "register");
                mv.addObject("error", "The verification code is incorrect or has expired!");
                return mv;
            }

            authService.activateUser(email);

            request.getSession().setAttribute("email", email);

            TKDoiTuongSuDung tk = tkRepository.findByTenDangNhap(email).orElse(null);

            if (tk == null || tk.getDoiTuongSuDung() == null) {
                mv.setViewName("login");
                mv.addObject("error", "Account profile not found! Please log in.");
                return mv;
            }

            mv.setViewName("redirect:/customer/account-setting");
            mv.addObject("user", tk.getDoiTuongSuDung());
            mv.addObject("role", "STUDENT");
            return mv;

        } catch (Exception e) {
            mv.setViewName("otp_verify");
            mv.addObject("email", email);
            mv.addObject("mode", "register");
            mv.addObject("error", "Server error: " + e.getMessage());
            return mv;
        }
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

        if (!email.contains("@")) {
            mv.setViewName("forgot_password");
            mv.addObject("error", "Invalid email format.");
            return mv;
        }

        TKDoiTuongSuDung tk = tkRepository.findByTenDangNhap(email).orElse(null);

        if (tk == null) {
            mv.setViewName("forgot_password");
            mv.addObject("error", "Account does not exist.");
            return mv;
        }

        if (!tk.isTrangThaiKichHoat()) {
            mv.setViewName("forgot_password");
            mv.addObject("unverifiedEmail", email);
            mv.addObject("error", "Your account is not verified yet.");
            return mv;
        }

        String code = emailService.createCode(email);
        emailService.sendResetPasswordEmail(email, code);

        mv.setViewName("otp_verify");
        mv.addObject("mode", "reset");
        mv.addObject("email", email);
        mv.addObject("message", "Please check your email for verification code to reset your password.");
        return mv;
    }

    @PostMapping("/verify-reset-pass")
    public ModelAndView verifyResetCode(HttpServletRequest request,
                                        @RequestParam String email,
                                        @RequestParam String code) {

        ModelAndView mv = new ModelAndView();

        if (!emailService.verify(email, code)) {
            mv.setViewName("otp_verify");
            mv.addObject("mode", "reset");
            mv.addObject("error", "Invalid or expired OTP.");
            mv.addObject("email", email);
            return mv;
        }

        // Lưu email vào session
        request.getSession().setAttribute("email", email);

        mv.setViewName("change_password");
        mv.addObject("mode", "reset");
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
            HttpServletRequest request,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        ModelAndView mv = new ModelAndView();

        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            mv.setViewName("login");
            mv.addObject("error", "Session expired. Please login again.");
            return mv;
        }

        if (!newPassword.equals(confirmPassword)) {
            mv.setViewName("change_password");
            mv.addObject("error", "Passwords do not match.");
            return mv;
        }

        TKDoiTuongSuDung tk = tkRepository.findByTenDangNhap(email).orElse(null);

        if (tk == null) {
            mv.setViewName("change_password");
            mv.addObject("error", "Account not found.");
            return mv;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        tk.setMatKhau(encoder.encode(newPassword));

        tkRepository.save(tk);

        mv.setViewName("login");
        mv.addObject("message", "Password updated successfully! Please log in again.");
        return mv;
    }
}

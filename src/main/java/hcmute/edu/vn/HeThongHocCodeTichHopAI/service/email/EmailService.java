package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.EmailVerification;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.EmailVerificationRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    // Tạo code
    @Override
    public String createCode(String email) {
        // Sinh mã ngẫu nhiên 6 chữ số, đảm bảo luôn có 6 ký tự
        String code = String.format("%06d", (int)(Math.random() * 1000000));

        EmailVerification ev = new EmailVerification();
        ev.setEmail(email);
        ev.setCode(code);
        ev.setCreatedAt(LocalDateTime.now());
        ev.setExpiredAt(LocalDateTime.now().plusMinutes(10)); // Mã hết hạn sau 10 phút

        emailVerificationRepository.save(ev);
        return code; // trả về mã để lớp khác gửi mail
    }

    // Gửi email khi đăng ký
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã xác thực tài khoản Hệ thống học code AI");
        message.setText(
                "Xin chào,\n\n" +
                        "Cảm ơn bạn đã đăng ký tài khoản Hệ thống học code AI.\n" +
                        "Mã xác thực của bạn là: " + code + "\n" +
                        "Mã này có hiệu lực trong 10 phút.\n\n" +
                        "Nếu bạn không yêu cầu đăng ký, vui lòng bỏ qua email này."
        );
        mailSender.send(message);
    }

    // Gửi email khi reset mật khẩu
    @Override
    public void sendResetPasswordEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Đặt lại mật khẩu Hệ thống học code AI");
        message.setText(
                "Xin chào,\n\n" +
                        "Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản của mình.\n" +
                        "Mã xác nhận đặt lại mật khẩu là: " + code + "\n" +
                        "Vui lòng nhập mã này trong trang Đặt lại mật khẩu.\n" +
                        "Mã có hiệu lực trong 10 phút.\n\n" +
                        "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này."
        );
        mailSender.send(message);
    }

    // Xác thực mã
    @Override
    public boolean verify(String email, String code) {
        EmailVerification ev = emailVerificationRepository.findById(email).orElse(null);
        if (ev == null) return false;
        if (!ev.getCode().equals(code)) return false;
        if (ev.getExpiredAt().isBefore(LocalDateTime.now())) return false;
        // Xác thực xong thì xoá mã cũ
        emailVerificationRepository.delete(ev);
        return true;
    }
}
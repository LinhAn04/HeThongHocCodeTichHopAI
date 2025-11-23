package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.EmailVerification;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.EmailVerificationRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    // Xóa code cũ trước khi tạo code mới
    private void deleteOldCode(String email) {
        EmailVerification old = emailVerificationRepository.findById(email).orElse(null);
        if (old != null) {
            emailVerificationRepository.delete(old);
        }
    }

    // Tạo code
    @Override
    public String createCode(String email) {

        // Xóa mã cũ trước khi tạo mã mới
        deleteOldCode(email);

        String code = String.format("%06d", (int)(Math.random() * 1000000));

        EmailVerification ev = new EmailVerification();
        ev.setEmail(email);
        ev.setCode(code);
        ev.setCreatedAt(LocalDateTime.now());
        ev.setExpiredAt(LocalDateTime.now().plusMinutes(10));

        emailVerificationRepository.save(ev);

        return code;
    }

    private String getVerificationHtml(String code) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Email Verification</title>
            </head>
            <body style="margin:0; padding:0; background-color:#f7f7f7; font-family:Arial, sans-serif;">

            <div style="max-width:600px; margin:40px auto; background:white; border-radius:8px; padding:40px; 
                        box-shadow:0 4px 12px rgba(0,0,0,0.08);">

                <h2 style="text-align:center; color:#111; margin-bottom:20px;">Email Verification</h2>

                <p style="font-size:15px; color:#333;">
                    Hi,<br><br>
                    Thank you for registering an account at <b>AI Code Learning</b>.<br>
                    Please use the verification code below to complete your registration.
                </p>

                <div style="text-align:center; margin:30px 0;">
                    <div style="
                        font-size:28px;
                        font-weight:bold;
                        letter-spacing:4px;
                        background:#111;
                        color:white;
                        display:inline-block;
                        padding:12px 20px;
                        border-radius:6px;">
                        """ + code + """
                    </div>
                </div>

                <p style="font-size:14px; color:#555;">
                    This code is valid for <b>10 minutes</b>.<br><br>
                    If you did not request this, you can safely ignore this email.
                </p>

                <hr style="border:none; border-top:1px solid #e5e5e5; margin:30px 0;">

                <p style="text-align:center; font-size:12px; color:#888;">
                    © 2025 AI Code Learning System<br>
                    This is an automated message. Please do not reply.
                </p>

            </div>

            </body>
            </html>
            """;
    }

    // Gửi email khi đăng ký
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Verification Code - AI Code Learning");

            String htmlContent = getVerificationHtml(code);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Error sending HTML email: " + e.getMessage());
        }
    }

    private String getResetPasswordHtml(String code) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Reset Password</title>
        </head>
        <body style="margin:0; padding:0; background-color:#f7f7f7; font-family:Arial, sans-serif;">

        <div style="max-width:600px; margin:40px auto; background:white; border-radius:8px; padding:40px;
                    box-shadow:0 4px 12px rgba(0,0,0,0.08);">

            <h2 style="text-align:center; color:#111; margin-bottom:20px;">Reset Your Password</h2>

            <p style="font-size:15px; color:#333;">
                Hi,<br><br>
                You requested to reset your password for <b>AI Code Learning</b>.<br>
                Please use the OTP code below to continue.
            </p>

            <div style="text-align:center; margin:30px 0;">
                <div style="
                    font-size:28px;
                    font-weight:bold;
                    letter-spacing:4px;
                    background:#111;
                    color:white;
                    display:inline-block;
                    padding:12px 20px;
                    border-radius:6px;">
                    """ + code + """
                </div>
            </div>

            <p style="font-size:14px; color:#555;">
                This OTP code is valid for <b>10 minutes</b>.<br><br>
                If you did not request this action, you can safely ignore this email.
            </p>

            <hr style="border:none; border-top:1px solid #e5e5e5; margin:30px 0;">

            <p style="text-align:center; font-size:12px; color:#888;">
                © 2025 AI Code Learning System<br>
                This is an automated message. Please do not reply.
            </p>

        </div>

        </body>
        </html>
        """;
    }

    // Gửi email khi reset mật khẩu
    @Override
    public void sendResetPasswordEmail(String toEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Reset your password - AI Code Learning");

            String html = getResetPasswordHtml(code);
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Error sending reset password email: " + e.getMessage());
        }
    }

    // Gửi lại mã xác thực (register)
    @Override
    public void resendVerificationCode(String email) {
        String code = createCode(email);
        sendVerificationCode(email, code);
    }

    // Gửi lại mã reset password
    @Override
    public void resendResetPasswordCode(String email) {
        String code = createCode(email);
        sendResetPasswordEmail(email, code);
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
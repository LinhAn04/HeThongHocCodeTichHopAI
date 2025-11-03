package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

public interface IEmailService {
    String createCode(String email);
    boolean verify(String email, String code);
    void sendVerificationCode(String toEmail, String code);
    void sendResetPasswordEmail(String toEmail, String code);
}
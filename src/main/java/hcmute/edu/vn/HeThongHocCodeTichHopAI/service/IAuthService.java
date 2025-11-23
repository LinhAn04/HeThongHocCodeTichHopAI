package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;

public interface IAuthService {
    TKDoiTuongSuDung register(String hoTen, String email, String password, String role);
    TKDoiTuongSuDung login(String email, String password);
    void activateUser(String email);
    void sendResetPasswordCode(String email);
    void resetPassword(String email, String code, String newPassword);
    boolean existsByEmail(String email);
}
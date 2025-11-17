package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;

public interface IAuthService {
    TKDoiTuongSuDung register(String hoTen, String email, String password, String role);
    TKDoiTuongSuDung login(String email, String password);
    void activateUser(String email);
}
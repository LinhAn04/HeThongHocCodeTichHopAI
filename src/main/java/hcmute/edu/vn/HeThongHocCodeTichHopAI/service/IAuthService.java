package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;

public interface IAuthService {
    TKDoiTuongSuDung register(String hoTen, String soDienThoai, String ngaySinh,
                              String gioiTinh, String email, String password);
    TKDoiTuongSuDung login(String email, String password);
    void activateUser(String email);
}
package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;

import java.util.List;

public interface ILichSuTruyCapKhoaHocService {
    List<LichSuTruyCapKhoaHoc> findAll();
    LichSuTruyCapKhoaHoc findById(String id);
    LichSuTruyCapKhoaHoc save(LichSuTruyCapKhoaHoc lichSuTruyCapKhoaHoc);
    void deleteById(String id);
    void saveOrUpdate(DoiTuongSuDung user, KhoaHoc khoaHoc);
}

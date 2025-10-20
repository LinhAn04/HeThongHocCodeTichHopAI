package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiKiemTra;

import java.util.List;

public interface IBaiKiemTraService {
    List<BaiKiemTra> findAll();
    BaiKiemTra findById(String id);
    BaiKiemTra save(BaiKiemTra baiKiemTra);
    void deleteById(String id);
}

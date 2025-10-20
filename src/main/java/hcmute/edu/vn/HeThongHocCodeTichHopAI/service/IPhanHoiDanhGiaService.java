package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.PhanHoiDanhGia;

import java.util.List;

public interface IPhanHoiDanhGiaService {
    List<PhanHoiDanhGia> findAll();
    PhanHoiDanhGia findById(String id);
    PhanHoiDanhGia save(PhanHoiDanhGia phanHoiDanhGia);
    void deleteById(String id);
}

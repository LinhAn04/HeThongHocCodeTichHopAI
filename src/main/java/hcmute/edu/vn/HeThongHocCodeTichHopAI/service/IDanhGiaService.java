package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DanhGia;

import java.util.List;

public interface IDanhGiaService {
    List<DanhGia> findAll();
    DanhGia findById(String id);
    DanhGia save(DanhGia danhGia);
    void deleteById(String id);
}

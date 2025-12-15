package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;

import java.util.List;

public interface IDangKyKhoaHocService {
    List<DangKyKhoaHoc> findAll();
    DangKyKhoaHoc findById(String id);
    DangKyKhoaHoc save(DangKyKhoaHoc dangKyKhoaHoc);
    void deleteById(String id);
    DangKyKhoaHoc findByUserAndCourse(DoiTuongSuDung user, KhoaHoc khoaHoc);
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TienDoHoc;

import java.util.List;
import java.util.Optional;

public interface ITienDoHocService {
    List<TienDoHoc> findAll();
    TienDoHoc findById(String id);
    TienDoHoc save(TienDoHoc tienDoHoc);
    void deleteById(String id);
//    Optional<TienDoHoc> findByNguoiHocAndKhoaHoc(
//            DoiTuongSuDung nguoiHoc,
//            KhoaHoc khoaHoc
//    );
}

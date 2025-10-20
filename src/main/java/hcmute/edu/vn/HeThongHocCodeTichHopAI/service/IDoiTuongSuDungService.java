package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;

import java.util.List;

public interface IDoiTuongSuDungService {
    List<DoiTuongSuDung> findAll();
    DoiTuongSuDung findById(String id);
    DoiTuongSuDung save(DoiTuongSuDung doiTuongSuDung);
    void deleteById(String id);
}

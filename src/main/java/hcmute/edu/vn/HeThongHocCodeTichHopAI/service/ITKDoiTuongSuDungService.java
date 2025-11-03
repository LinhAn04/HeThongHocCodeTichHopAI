package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;

import java.util.List;

public interface ITKDoiTuongSuDungService {
    List<TKDoiTuongSuDung> findAll();
    TKDoiTuongSuDung findById(String id);
    TKDoiTuongSuDung save(TKDoiTuongSuDung tkDoiTuongSuDung);
    void deleteById(String id);
}
package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TienDoHoc;

import java.util.List;

public interface ITienDoHocService {
    List<TienDoHoc> findAll();
    TienDoHoc findById(String id);
    TienDoHoc save(TienDoHoc tienDoHoc);
    void deleteById(String id);
}

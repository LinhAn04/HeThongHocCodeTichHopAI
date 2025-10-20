package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoTrinhHoc;

import java.util.List;

public interface ILoTrinhHocService {
    List<LoTrinhHoc> findAll();
    LoTrinhHoc findById(String id);
    LoTrinhHoc save(LoTrinhHoc loTrinhHoc);
    void deleteById(String id);
}

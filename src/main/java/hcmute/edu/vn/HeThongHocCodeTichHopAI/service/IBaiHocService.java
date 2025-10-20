package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;

import java.util.List;

public interface IBaiHocService {
    List<BaiHoc> findAll();
    BaiHoc findById(String id);
    BaiHoc save(BaiHoc baiHoc);
    void deleteById(String id);
}

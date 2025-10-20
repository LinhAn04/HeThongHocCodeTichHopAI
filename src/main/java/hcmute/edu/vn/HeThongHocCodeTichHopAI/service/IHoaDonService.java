package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.HoaDon;

import java.util.List;

public interface IHoaDonService {
    List<HoaDon> findAll();
    HoaDon findById(String id);
    HoaDon save(HoaDon hoaDon);
    void deleteById(String id);
}

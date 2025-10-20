package hcmute.edu.vn.HeThongHocCodeTichHopAI.service;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.ThongBao;

import java.util.List;

public interface IThongBaoService {
    List<ThongBao> findAll();
    ThongBao findById(String id);
    ThongBao save(ThongBao thongBao);
    void deleteById(String id);
}

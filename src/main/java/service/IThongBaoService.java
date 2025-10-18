package service;

import model.ThongBao;

import java.util.List;

public interface IThongBaoService {
    List<ThongBao> findAll();
    ThongBao findById(int id);
    ThongBao save(ThongBao thongBao);
    void deleteById(int id);
}

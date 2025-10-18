package service;

import model.BaiKiemTra;

import java.util.List;

public interface IBaiKiemTraService {
    List<BaiKiemTra> findAll();
    BaiKiemTra findById(int id);
    BaiKiemTra save(BaiKiemTra baiKiemTra);
    void deleteById(int id);
}

package service;

import model.PhanHoiDanhGia;

import java.util.List;

public interface IPhanHoiDanhGiaService {
    List<PhanHoiDanhGia> findAll();
    PhanHoiDanhGia findById(int id);
    PhanHoiDanhGia save(PhanHoiDanhGia phanHoiDanhGia);
    void deleteById(int id);
}

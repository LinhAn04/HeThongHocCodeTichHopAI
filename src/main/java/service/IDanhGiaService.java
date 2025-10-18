package service;

import model.DanhGia;

import java.util.List;

public interface IDanhGiaService {
    List<DanhGia> findAll();
    DanhGia findById(int id);
    DanhGia save(DanhGia danhGia);
    void deleteById(int id);
}

package service;

import model.KhoaHoc;

import java.util.List;

public interface IKhoaHocService {
    List<KhoaHoc> findAll();
    KhoaHoc findById(int id);
    KhoaHoc save(KhoaHoc khoaHoc);
    void deleteById(int id);
}

package service;

import model.TienDoHoc;

import java.util.List;

public interface ITienDoHocService {
    List<TienDoHoc> findAll();
    TienDoHoc findById(int id);
    TienDoHoc save(TienDoHoc tienDoHoc);
    void deleteById(int id);
}

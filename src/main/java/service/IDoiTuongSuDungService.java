package service;

import model.DoiTuongSuDung;

import java.util.List;

public interface IDoiTuongSuDungService {
    List<DoiTuongSuDung> findAll();
    DoiTuongSuDung findById(int id);
    DoiTuongSuDung save(DoiTuongSuDung doiTuongSuDung);
    void deleteById(int id);
}

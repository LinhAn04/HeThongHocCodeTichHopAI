package service;

import model.TKDoiTuongSuDung;

import java.util.List;

public interface ITKDoiTuongSuDungService {
    List<TKDoiTuongSuDung> findAll();
    TKDoiTuongSuDung findById(int id);
    TKDoiTuongSuDung save(TKDoiTuongSuDung tkDoiTuongSuDung);
    void deleteById(int id);
}

package service;

import model.LoTrinhHoc;

import java.util.List;

public interface ILoTrinhHocService {
    List<LoTrinhHoc> findAll();
    LoTrinhHoc findById(int id);
    LoTrinhHoc save(LoTrinhHoc loTrinhHoc);
    void deleteById(int id);
}

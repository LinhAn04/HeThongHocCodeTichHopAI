package service;

import model.BaiHoc;

import java.util.List;

public interface IBaiHocService {
    List<BaiHoc> findAll();
    BaiHoc findById(int id);
    BaiHoc save(BaiHoc baiHoc);
    void deleteById(int id);
}

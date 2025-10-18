package service;

import model.HoaDon;

import java.util.List;

public interface IHoaDonService {
    List<HoaDon> findAll();
    HoaDon findById(int id);
    HoaDon save(HoaDon hoaDon);
    void deleteById(int id);
}

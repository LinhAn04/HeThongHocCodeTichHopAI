package controller;

import model.PhanHoiDanhGia;
import org.springframework.web.bind.annotation.*;
import service.IPhanHoiDanhGiaService;

import java.util.List;

@RestController
@RequestMapping("/api/phanhoidanhgia")
public class PhanHoiDanhGiaController {
    private final IPhanHoiDanhGiaService phanHoiDanhGiaService;

    public PhanHoiDanhGiaController(IPhanHoiDanhGiaService phanHoiDanhGiaService) {
        this.phanHoiDanhGiaService = phanHoiDanhGiaService;
    }

    @GetMapping
    public List<PhanHoiDanhGia> getAll() {
        return phanHoiDanhGiaService.findAll();
    }

    @GetMapping("/{id}")
    public PhanHoiDanhGia getById(@PathVariable int id) {
        return phanHoiDanhGiaService.findById(id);
    }

    @PostMapping
    public PhanHoiDanhGia create(@RequestBody PhanHoiDanhGia phanHoiDanhGia) {
        return phanHoiDanhGiaService.save(phanHoiDanhGia);
    }

    @PutMapping("/{id}")
    public PhanHoiDanhGia update(@PathVariable int id, @RequestBody PhanHoiDanhGia phanHoiDanhGia) {
        phanHoiDanhGia.setIdPhanHoiDanhGia(id);
        return phanHoiDanhGiaService.save(phanHoiDanhGia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        phanHoiDanhGiaService.deleteById(id);
    }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.PhanHoiDanhGia;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IPhanHoiDanhGiaService;

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
    public PhanHoiDanhGia getById(@PathVariable String id) {
        return phanHoiDanhGiaService.findById(id);
    }

    @PostMapping
    public PhanHoiDanhGia create(@RequestBody PhanHoiDanhGia phanHoiDanhGia) {
        return phanHoiDanhGiaService.save(phanHoiDanhGia);
    }

    @PutMapping("/{id}")
    public PhanHoiDanhGia update(@PathVariable String id, @RequestBody PhanHoiDanhGia phanHoiDanhGia) {
        phanHoiDanhGia.setIdPhanHoiDanhGia(id);
        return phanHoiDanhGiaService.save(phanHoiDanhGia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        phanHoiDanhGiaService.deleteById(id);
    }
}

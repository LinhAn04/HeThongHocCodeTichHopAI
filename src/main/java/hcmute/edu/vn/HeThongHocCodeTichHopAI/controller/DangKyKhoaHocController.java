package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDangKyKhoaHocService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dangkykhoahoc")
public class DangKyKhoaHocController {
    private final IDangKyKhoaHocService dangKyKhoaHocService;

    public DangKyKhoaHocController(IDangKyKhoaHocService dangKyKhoaHocService) {
        this.dangKyKhoaHocService = dangKyKhoaHocService;
    }

    @GetMapping
    public List<DangKyKhoaHoc> getAll() {
        return dangKyKhoaHocService.findAll();
    }

    @GetMapping("/{id}")
    public DangKyKhoaHoc getById(@PathVariable String id) {
        return dangKyKhoaHocService.findById(id);
    }

    @PostMapping
    public DangKyKhoaHoc create(@RequestBody DangKyKhoaHoc dangKyKhoaHoc) {
        return dangKyKhoaHocService.save(dangKyKhoaHoc);
    }

    @PutMapping("/{id}")
    public DangKyKhoaHoc update(@PathVariable String id, @RequestBody DangKyKhoaHoc dangKyKhoaHoc) {
        dangKyKhoaHoc.setIdDangKyKhoaHoc(id);
        return dangKyKhoaHocService.save(dangKyKhoaHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        dangKyKhoaHocService.deleteById(id);
    }
}

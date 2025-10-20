package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiKiemTra;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiKiemTraService;

import java.util.List;

@RestController
@RequestMapping("/api/baikiemtra")
public class BaiKiemTraController {
    private final IBaiKiemTraService baiKiemTraService;

    public BaiKiemTraController(IBaiKiemTraService baiKiemTraService) {
        this.baiKiemTraService = baiKiemTraService;
    }

    @GetMapping
    public List<BaiKiemTra> getAll() {
        return baiKiemTraService.findAll();
    }

    @GetMapping("/{id}")
    public BaiKiemTra getById(@PathVariable String id) {
        return baiKiemTraService.findById(id);
    }

    @PostMapping
    public BaiKiemTra create(@RequestBody BaiKiemTra baiKiemTra) {
        return baiKiemTraService.save(baiKiemTra);
    }

    @PutMapping("/{id}")
    public BaiKiemTra update(@PathVariable String id, @RequestBody BaiKiemTra baiKiemTra) {
        baiKiemTra.setIdBaiKiemTra(id);
        return baiKiemTraService.save(baiKiemTra);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        baiKiemTraService.deleteById(id);
    }
}

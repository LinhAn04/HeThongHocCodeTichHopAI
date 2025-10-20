package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IKhoaHocService;

import java.util.List;

@RestController
@RequestMapping("/api/khoahoc")
public class KhoaHocController {
    private final IKhoaHocService khoaHocService;

    public KhoaHocController(IKhoaHocService khoaHocService) {
        this.khoaHocService = khoaHocService;
    }

    @GetMapping
    public List<KhoaHoc> getAll() {
        return khoaHocService.findAll();
    }

    @GetMapping("/{id}")
    public KhoaHoc getById(@PathVariable String id) {
        return khoaHocService.findById(id);
    }

    @PostMapping
    public KhoaHoc create(@RequestBody KhoaHoc khoaHoc) {
        return khoaHocService.save(khoaHoc);
    }

    @PutMapping("/{id}")
    public KhoaHoc update(@PathVariable String id, @RequestBody KhoaHoc khoaHoc) {
        khoaHoc.setIdKhoaHoc(id);
        return khoaHocService.save(khoaHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        khoaHocService.deleteById(id);
    }
}

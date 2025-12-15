package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ILichSuTruyCapKhoaHocService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/líichsutruycapkhoahoc")
public class LichSuTruyCapKhoaHocController {
    private final ILichSuTruyCapKhoaHocService lichSuTruyCapKhoaHocService;

    public LichSuTruyCapKhoaHocController(ILichSuTruyCapKhoaHocService lichSuTruyCapKhoaHocService) {
        this.lichSuTruyCapKhoaHocService = lichSuTruyCapKhoaHocService;
    }

    @GetMapping
    public List<LichSuTruyCapKhoaHoc> getAll() {
        return lichSuTruyCapKhoaHocService.findAll();
    }

    @GetMapping("/{id}")
    public LichSuTruyCapKhoaHoc getById(@PathVariable String id) {
        return lichSuTruyCapKhoaHocService.findById(id);
    }

    @PostMapping
    public LichSuTruyCapKhoaHoc create(@RequestBody LichSuTruyCapKhoaHoc lichSuTruyCapKhoaHoc) {
        return lichSuTruyCapKhoaHocService.save(lichSuTruyCapKhoaHoc);
    }

    @PutMapping("/{id}")
    public LichSuTruyCapKhoaHoc update(@PathVariable String id, @RequestBody LichSuTruyCapKhoaHoc lichSuTruyCapKhoaHoc) {
        lichSuTruyCapKhoaHoc.setIdLichSuTruyCapKhoaHoc(id);
        return lichSuTruyCapKhoaHocService.save(lichSuTruyCapKhoaHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        lichSuTruyCapKhoaHocService.deleteById(id);
    }
}

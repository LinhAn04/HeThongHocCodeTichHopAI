package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.HoaDon;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IHoaDonService;

import java.util.List;

@RestController
@RequestMapping("/api/hoadon")
public class HoaDonController {
    private final IHoaDonService hoaDonService;

    public HoaDonController(IHoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @GetMapping
    public List<HoaDon> getAll() {
        return hoaDonService.findAll();
    }

    @GetMapping("/{id}")
    public HoaDon getById(@PathVariable String id) {
        return hoaDonService.findById(id);
    }

    @PostMapping
    public HoaDon create(@RequestBody HoaDon hoaDon) {
        return hoaDonService.save(hoaDon);
    }

    @PutMapping("/{id}")
    public HoaDon update(@PathVariable String id, @RequestBody HoaDon hoaDon) {
        hoaDon.setIdHoaDon(id);
        return hoaDonService.save(hoaDon);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        hoaDonService.deleteById(id);
    }
}
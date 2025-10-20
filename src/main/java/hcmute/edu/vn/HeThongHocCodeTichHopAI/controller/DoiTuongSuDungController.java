package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;

import java.util.List;

@RestController
@RequestMapping("/api/doituongsudung")
public class DoiTuongSuDungController {
    private final IDoiTuongSuDungService doiTuongSuDungService;

    public DoiTuongSuDungController(IDoiTuongSuDungService doiTuongSuDungService) {
        this.doiTuongSuDungService = doiTuongSuDungService;
    }

    @GetMapping
    public List<DoiTuongSuDung> getAll() {
        return doiTuongSuDungService.findAll();
    }

    @GetMapping("/{id}")
    public DoiTuongSuDung getById(@PathVariable String id) {
        return doiTuongSuDungService.findById(id);
    }

    @PostMapping
    public DoiTuongSuDung create(@RequestBody DoiTuongSuDung doiTuongSuDung) {
        return doiTuongSuDungService.save(doiTuongSuDung);
    }

    @PutMapping("/{id}")
    public DoiTuongSuDung update(@PathVariable String id, @RequestBody DoiTuongSuDung doiTuongSuDung) {
        doiTuongSuDung.setIdDoiTuong(id);
        return doiTuongSuDungService.save(doiTuongSuDung);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        doiTuongSuDungService.deleteById(id);
    }
}

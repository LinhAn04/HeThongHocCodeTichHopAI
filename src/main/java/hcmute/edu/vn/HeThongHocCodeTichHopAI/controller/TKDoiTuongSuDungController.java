package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITKDoiTuongSuDungService;

import java.util.List;

@RestController
@RequestMapping("/api/tkdoituongsudung")
public class TKDoiTuongSuDungController {
    private final ITKDoiTuongSuDungService tkDoiTuongSuDungService;

    public TKDoiTuongSuDungController(ITKDoiTuongSuDungService tkDoiTuongSuDungService) {
        this.tkDoiTuongSuDungService = tkDoiTuongSuDungService;
    }

    @GetMapping
    public List<TKDoiTuongSuDung> getAll() {
        return tkDoiTuongSuDungService.findAll();
    }

    @GetMapping("/{id}")
    public TKDoiTuongSuDung getById(@PathVariable String id) {
        return tkDoiTuongSuDungService.findById(id);
    }

    @PostMapping
    public TKDoiTuongSuDung create(@RequestBody TKDoiTuongSuDung tkDoiTuongSuDung) {
        return tkDoiTuongSuDungService.save(tkDoiTuongSuDung);
    }

    @PutMapping("/{id}")
    public TKDoiTuongSuDung update(@PathVariable String id, @RequestBody TKDoiTuongSuDung tkDoiTuongSuDung) {
        tkDoiTuongSuDung.setIdTKDoiTuong(id);
        return tkDoiTuongSuDungService.save(tkDoiTuongSuDung);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        tkDoiTuongSuDungService.deleteById(id);
    }
}

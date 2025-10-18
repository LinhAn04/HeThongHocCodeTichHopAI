package controller;

import model.DanhGia;
import model.DoiTuongSuDung;
import org.springframework.web.bind.annotation.*;
import service.IDanhGiaService;
import service.IDoiTuongSuDungService;

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
    public DoiTuongSuDung getById(@PathVariable int id) {
        return doiTuongSuDungService.findById(id);
    }

    @PostMapping
    public DoiTuongSuDung create(@RequestBody DoiTuongSuDung doiTuongSuDung) {
        return doiTuongSuDungService.save(doiTuongSuDung);
    }

    @PutMapping("/{id}")
    public DoiTuongSuDung update(@PathVariable int id, @RequestBody DoiTuongSuDung doiTuongSuDung) {
        doiTuongSuDung.setIdDoiTuong(id);
        return doiTuongSuDungService.save(doiTuongSuDung);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        doiTuongSuDungService.deleteById(id);
    }
}

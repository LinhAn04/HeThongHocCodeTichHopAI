package controller;

import model.DanhGia;
import org.springframework.web.bind.annotation.*;
import service.IDanhGiaService;

import java.util.List;

@RestController
@RequestMapping("/api/danhgia")
public class DanhGiaController {
    private final IDanhGiaService danhGiaService;

    public DanhGiaController(IDanhGiaService danhGiaService) {
        this.danhGiaService = danhGiaService;
    }

    @GetMapping
    public List<DanhGia> getAll() {
        return danhGiaService.findAll();
    }

    @GetMapping("/{id}")
    public DanhGia getById(@PathVariable int id) {
        return danhGiaService.findById(id);
    }

    @PostMapping
    public DanhGia create(@RequestBody DanhGia danhGia) {
        return danhGiaService.save(danhGia);
    }

    @PutMapping("/{id}")
    public DanhGia update(@PathVariable int id, @RequestBody DanhGia danhGia) {
        danhGia.setIdDanhGia(id);
        return danhGiaService.save(danhGia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        danhGiaService.deleteById(id);
    }
}

package controller;

import model.TienDoHoc;
import org.springframework.web.bind.annotation.*;
import service.ITienDoHocService;

import java.util.List;

@RestController
@RequestMapping("/api/tiendohoc")
public class TienDoHocController {
    private final ITienDoHocService tienDoHocService;

    public TienDoHocController(ITienDoHocService tienDoHocService) {
        this.tienDoHocService = tienDoHocService;
    }

    @GetMapping
    public List<TienDoHoc> getAll() {
        return tienDoHocService.findAll();
    }

    @GetMapping("/{id}")
    public TienDoHoc getById(@PathVariable int id) {
        return tienDoHocService.findById(id);
    }

    @PostMapping
    public TienDoHoc create(@RequestBody TienDoHoc tienDoHoc) {
        return tienDoHocService.save(tienDoHoc);
    }

    @PutMapping("/{id}")
    public TienDoHoc update(@PathVariable int id, @RequestBody TienDoHoc tienDoHoc) {
        tienDoHoc.setIdTienDoHoc(id);
        return tienDoHocService.save(tienDoHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        tienDoHocService.deleteById(id);
    }
}

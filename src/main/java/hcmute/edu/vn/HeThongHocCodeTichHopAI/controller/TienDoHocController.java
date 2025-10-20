package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TienDoHoc;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITienDoHocService;

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
    public TienDoHoc getById(@PathVariable String id) {
        return tienDoHocService.findById(id);
    }

    @PostMapping
    public TienDoHoc create(@RequestBody TienDoHoc tienDoHoc) {
        return tienDoHocService.save(tienDoHoc);
    }

    @PutMapping("/{id}")
    public TienDoHoc update(@PathVariable String id, @RequestBody TienDoHoc tienDoHoc) {
        tienDoHoc.setIdTienDoHoc(id);
        return tienDoHocService.save(tienDoHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        tienDoHocService.deleteById(id);
    }
}

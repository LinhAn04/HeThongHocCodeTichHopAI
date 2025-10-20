package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoTrinhHoc;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ILoTrinhHocService;

import java.util.List;

@RestController
@RequestMapping("/api/lotrinhhoc")
public class LoTrinhHocController {
    private final ILoTrinhHocService lotrinhHocService;

    public LoTrinhHocController(ILoTrinhHocService lotrinhHocService) {
        this.lotrinhHocService = lotrinhHocService;
    }

    @GetMapping
    public List<LoTrinhHoc> getAll() {
        return lotrinhHocService.findAll();
    }

    @GetMapping("/{id}")
    public LoTrinhHoc getById(@PathVariable String id) {
        return lotrinhHocService.findById(id);
    }

    @PostMapping
    public LoTrinhHoc create(@RequestBody LoTrinhHoc lotrinhHoc) {
        return lotrinhHocService.save(lotrinhHoc);
    }

    @PutMapping("/{id}")
    public LoTrinhHoc update(@PathVariable String id, @RequestBody LoTrinhHoc lotrinhHoc) {
        lotrinhHoc.setIdLoTrinhHoc(id);
        return lotrinhHocService.save(lotrinhHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        lotrinhHocService.deleteById(id);
    }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiHocService;

import java.util.List;

@RestController
@RequestMapping("/api/baihoc")
public class BaiHocController {
    private final IBaiHocService baiHocService;

    public BaiHocController(IBaiHocService baiHocService) {
        this.baiHocService = baiHocService;
    }

    @GetMapping
    public List<BaiHoc> getAll() {
        return baiHocService.findAll();
    }

    @GetMapping("/{id}")
    public BaiHoc getById(@PathVariable String id) {
        return baiHocService.findById(id);
    }

    @PostMapping
    public BaiHoc create(@RequestBody BaiHoc baiHoc) {
        return baiHocService.save(baiHoc);
    }

    @PutMapping("/{id}")
    public BaiHoc update(@PathVariable String id, @RequestBody BaiHoc baiHoc) {
        baiHoc.setIdBaiHoc(id);
        return baiHocService.save(baiHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        baiHocService.deleteById(id);
    }
}

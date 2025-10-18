package controller;

import model.BaiHoc;
import org.springframework.web.bind.annotation.*;
import service.IBaiHocService;

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
    public BaiHoc getById(@PathVariable int id) {
        return baiHocService.findById(id);
    }

    @PostMapping
    public BaiHoc create(@RequestBody BaiHoc baiHoc) {
        return baiHocService.save(baiHoc);
    }

    @PutMapping("/{id}")
    public BaiHoc update(@PathVariable int id, @RequestBody BaiHoc baiHoc) {
        baiHoc.setIdBaiHoc(id);
        return baiHocService.save(baiHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        baiHocService.deleteById(id);
    }
}

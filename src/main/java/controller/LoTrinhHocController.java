package controller;

import model.LoTrinhHoc;
import org.springframework.web.bind.annotation.*;
import service.ILoTrinhHocService;

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
    public LoTrinhHoc getById(@PathVariable int id) {
        return lotrinhHocService.findById(id);
    }

    @PostMapping
    public LoTrinhHoc create(@RequestBody LoTrinhHoc lotrinhHoc) {
        return lotrinhHocService.save(lotrinhHoc);
    }

    @PutMapping("/{id}")
    public LoTrinhHoc update(@PathVariable int id, @RequestBody LoTrinhHoc lotrinhHoc) {
        lotrinhHoc.setIdLoTrinhHoc(id);
        return lotrinhHocService.save(lotrinhHoc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        lotrinhHocService.deleteById(id);
    }
}

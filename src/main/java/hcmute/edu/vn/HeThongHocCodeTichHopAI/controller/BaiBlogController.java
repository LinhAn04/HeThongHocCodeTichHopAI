package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;
import org.springframework.web.bind.annotation.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiBlogService;

import java.util.List;

@RestController
@RequestMapping("/api/baiblog")
public class BaiBlogController {
    private final IBaiBlogService baiBlogService;

    public BaiBlogController(IBaiBlogService baiBlogService) {
        this.baiBlogService = baiBlogService;
    }

    @GetMapping
    public List<BaiBlog> getAll() {
        return baiBlogService.findAll();
    }

    @GetMapping("/{id}")
    public BaiBlog getById(@PathVariable String id) {
        return baiBlogService.findById(id);
    }

    @PostMapping
    public BaiBlog create(@RequestBody BaiBlog baiBlog) {
        return baiBlogService.save(baiBlog);
    }

    @PutMapping("/{id}")
    public BaiBlog update(@PathVariable String id, @RequestBody BaiBlog baiBlog) {
        baiBlog.setIdBaiBlog(id);
        return baiBlogService.save(baiBlog);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        baiBlogService.deleteById(id);
    }
}

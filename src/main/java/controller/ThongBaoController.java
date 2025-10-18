package controller;

import model.ThongBao;
import org.springframework.web.bind.annotation.*;
import service.IThongBaoService;

import java.util.List;

@RestController
@RequestMapping("/api/thongbao")
public class ThongBaoController {
    private final IThongBaoService thongBaoService;

    public ThongBaoController(IThongBaoService thongBaoService) {
        this.thongBaoService = thongBaoService;
    }

    @GetMapping
    public List<ThongBao> getAll() {
        return thongBaoService.findAll();
    }

    @GetMapping("/{id}")
    public ThongBao getById(@PathVariable int id) {
        return thongBaoService.findById(id);
    }

    @PostMapping
    public ThongBao create(@RequestBody ThongBao thongBao) {
        return thongBaoService.save(thongBao);
    }

    @PutMapping("/{id}")
    public ThongBao update(@PathVariable int id, @RequestBody ThongBao thongBao) {
        thongBao.setIdThongBao(id);
        return thongBaoService.save(thongBao);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        thongBaoService.deleteById(id);
    }
}

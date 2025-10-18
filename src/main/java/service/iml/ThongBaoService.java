package service.iml;

import model.ThongBao;
import org.springframework.stereotype.Service;
import repository.ThongBaoRepository;
import service.IThongBaoService;

import java.util.List;

@Service
public class ThongBaoService implements IThongBaoService {
    private final ThongBaoRepository thongBaoRepository;

    public ThongBaoService(ThongBaoRepository thongBaoRepository) {
        this.thongBaoRepository = thongBaoRepository;
    }

    @Override
    public List<ThongBao> findAll() {
        return thongBaoRepository.findAll();
    }

    @Override
    public ThongBao findById(int id) {
        return thongBaoRepository.findById(id).orElse(null);
    }

    @Override
    public ThongBao save(ThongBao thongBao) {
        return thongBaoRepository.save(thongBao);
    }

    @Override
    public void deleteById(int id) {
        thongBaoRepository.deleteById(id);
    }
}
package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.ThongBao;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.ThongBaoRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IThongBaoService;

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
    public ThongBao findById(String id) {
        return thongBaoRepository.findById(id).orElse(null);
    }

    @Override
    public ThongBao save(ThongBao thongBao) {
        return thongBaoRepository.save(thongBao);
    }

    @Override
    public void deleteById(String id) {
        thongBaoRepository.deleteById(id);
    }
}
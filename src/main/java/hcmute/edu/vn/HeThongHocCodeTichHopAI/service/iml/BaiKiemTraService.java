package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiKiemTra;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.BaiKiemTraRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiKiemTraService;

import java.util.List;

@Service
public class BaiKiemTraService implements IBaiKiemTraService {
    private final BaiKiemTraRepository baiKiemTraRepository;

    public BaiKiemTraService(BaiKiemTraRepository baiKiemTraRepository) {
        this.baiKiemTraRepository = baiKiemTraRepository;
    }

    @Override
    public List<BaiKiemTra> findAll() {
        return baiKiemTraRepository.findAll();
    }

    @Override
    public BaiKiemTra findById(String id) {
        return baiKiemTraRepository.findById(id).orElse(null);
    }

    @Override
    public BaiKiemTra save(BaiKiemTra baiKiemTra) {
        return baiKiemTraRepository.save(baiKiemTra);
    }

    @Override
    public void deleteById(String id) {
        baiKiemTraRepository.deleteById(id);
    }
}

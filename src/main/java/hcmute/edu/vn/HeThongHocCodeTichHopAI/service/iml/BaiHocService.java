package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.BaiHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiHocService;
import java.util.List;

@Service
public class BaiHocService implements IBaiHocService {
    private final BaiHocRepository baiHocRepository;

    public BaiHocService(BaiHocRepository baiHocRepository) {
        this.baiHocRepository = baiHocRepository;
    }

    @Override
    public List<BaiHoc> findAll() {
        return baiHocRepository.findAll();
    }

    @Override
    public BaiHoc findById(String id) {
        return baiHocRepository.findById(id).orElse(null);
    }

    @Override
    public BaiHoc save(BaiHoc baiHoc) {
        return baiHocRepository.save(baiHoc);
    }

    @Override
    public void deleteById(String id) {
        baiHocRepository.deleteById(id);
    }
}
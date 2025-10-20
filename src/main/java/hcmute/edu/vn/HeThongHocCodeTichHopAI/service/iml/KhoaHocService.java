package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.KhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IKhoaHocService;

import java.util.List;

@Service
public class KhoaHocService implements IKhoaHocService {
    private final KhoaHocRepository khoaHocRepository;

    public KhoaHocService(KhoaHocRepository khoaHocRepository) {
        this.khoaHocRepository = khoaHocRepository;
    }

    @Override
    public List<KhoaHoc> findAll() {
        return khoaHocRepository.findAll();
    }

    @Override
    public KhoaHoc findById(String id) {
        return khoaHocRepository.findById(id).orElse(null);
    }

    @Override
    public KhoaHoc save(KhoaHoc khoaHoc) {
        return khoaHocRepository.save(khoaHoc);
    }

    @Override
    public void deleteById(String id) {
        khoaHocRepository.deleteById(id);
    }
}
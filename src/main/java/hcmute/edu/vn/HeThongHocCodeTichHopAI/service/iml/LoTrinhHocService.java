package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoTrinhHoc;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.LoTrinhHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ILoTrinhHocService;

import java.util.List;

@Service
public class LoTrinhHocService implements ILoTrinhHocService {
    private final LoTrinhHocRepository loTrinhHocRepository;

    public LoTrinhHocService(LoTrinhHocRepository loTrinhHocRepository) {
        this.loTrinhHocRepository = loTrinhHocRepository;
    }

    @Override
    public List<LoTrinhHoc> findAll() {
        return loTrinhHocRepository.findAll();
    }

    @Override
    public LoTrinhHoc findById(String id) {
        return loTrinhHocRepository.findById(id).orElse(null);
    }

    @Override
    public LoTrinhHoc save(LoTrinhHoc loTrinhHoc) {
        return loTrinhHocRepository.save(loTrinhHoc);
    }

    @Override
    public void deleteById(String id) {
        loTrinhHocRepository.deleteById(id);
    }
}

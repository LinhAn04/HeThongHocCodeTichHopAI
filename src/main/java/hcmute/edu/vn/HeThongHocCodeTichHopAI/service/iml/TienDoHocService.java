package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TienDoHoc;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TienDoHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITienDoHocService;

import java.util.List;

@Service
public class TienDoHocService implements ITienDoHocService {
    private final TienDoHocRepository tienDoHocRepository;

    public TienDoHocService(TienDoHocRepository tienDoHocRepository) {
        this.tienDoHocRepository = tienDoHocRepository;
    }

    @Override
    public List<TienDoHoc> findAll() {
        return tienDoHocRepository.findAll();
    }

    @Override
    public TienDoHoc findById(String id) {
        return tienDoHocRepository.findById(id).orElse(null);
    }

    @Override
    public TienDoHoc save(TienDoHoc tienDoHoc) {
        return tienDoHocRepository.save(tienDoHoc);
    }

    @Override
    public void deleteById(String id) {
        tienDoHocRepository.deleteById(id);
    }
}
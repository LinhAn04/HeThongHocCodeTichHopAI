package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DanhGia;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DanhGiaRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDanhGiaService;

import java.util.List;

@Service
public class DanhGiaService implements IDanhGiaService {
    private final DanhGiaRepository danhGiaRepository;

    public DanhGiaService(DanhGiaRepository danhGiaRepository) {
        this.danhGiaRepository = danhGiaRepository;
    }

    @Override
    public List<DanhGia> findAll() {
        return danhGiaRepository.findAll();
    }

    @Override
    public DanhGia findById(String id) {
        return danhGiaRepository.findById(id).orElse(null);
    }

    @Override
    public DanhGia save(DanhGia danhGia) {
        return danhGiaRepository.save(danhGia);
    }

    @Override
    public void deleteById(String id) {
        danhGiaRepository.deleteById(id);
    }
}

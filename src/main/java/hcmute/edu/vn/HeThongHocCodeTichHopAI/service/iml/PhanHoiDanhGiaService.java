package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.PhanHoiDanhGia;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.PhanHoiDanhGiaRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IPhanHoiDanhGiaService;

import java.util.List;

@Service
public class PhanHoiDanhGiaService implements IPhanHoiDanhGiaService {
    private final PhanHoiDanhGiaRepository phanHoiDanhGiaRepository;

    public PhanHoiDanhGiaService(PhanHoiDanhGiaRepository phanHoiDanhGiaRepository) {
        this.phanHoiDanhGiaRepository = phanHoiDanhGiaRepository;
    }

    @Override
    public List<PhanHoiDanhGia> findAll() {
        return phanHoiDanhGiaRepository.findAll();
    }

    @Override
    public PhanHoiDanhGia findById(String id) {
        return phanHoiDanhGiaRepository.findById(id).orElse(null);
    }

    @Override
    public PhanHoiDanhGia save(PhanHoiDanhGia phanHoiDanhGia) {
        return phanHoiDanhGiaRepository.save(phanHoiDanhGia);
    }

    @Override
    public void deleteById(String id) {
        phanHoiDanhGiaRepository.deleteById(id);
    }
}

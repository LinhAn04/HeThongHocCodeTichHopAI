package service.iml;

import model.PhanHoiDanhGia;
import org.springframework.stereotype.Service;
import repository.PhanHoiDanhGiaRepository;
import service.IPhanHoiDanhGiaService;

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
    public PhanHoiDanhGia findById(int id) {
        return phanHoiDanhGiaRepository.findById(id).orElse(null);
    }

    @Override
    public PhanHoiDanhGia save(PhanHoiDanhGia phanHoiDanhGia) {
        return phanHoiDanhGiaRepository.save(phanHoiDanhGia);
    }

    @Override
    public void deleteById(int id) {
        phanHoiDanhGiaRepository.deleteById(id);
    }
}

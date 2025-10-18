package service.iml;

import model.DanhGia;
import org.springframework.stereotype.Service;
import repository.DanhGiaRepository;
import service.IDanhGiaService;

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
    public DanhGia findById(int id) {
        return danhGiaRepository.findById(id).orElse(null);
    }

    @Override
    public DanhGia save(DanhGia danhGia) {
        return danhGiaRepository.save(danhGia);
    }

    @Override
    public void deleteById(int id) {
        danhGiaRepository.deleteById(id);
    }
}

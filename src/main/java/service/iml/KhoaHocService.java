package service.iml;

import model.KhoaHoc;
import org.springframework.stereotype.Service;
import repository.KhoaHocRepository;
import service.IKhoaHocService;

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
    public KhoaHoc findById(int id) {
        return khoaHocRepository.findById(id).orElse(null);
    }

    @Override
    public KhoaHoc save(KhoaHoc khoaHoc) {
        return khoaHocRepository.save(khoaHoc);
    }

    @Override
    public void deleteById(int id) {
        khoaHocRepository.deleteById(id);
    }
}
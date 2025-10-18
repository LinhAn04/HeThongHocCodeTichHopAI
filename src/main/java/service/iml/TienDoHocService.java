package service.iml;

import model.TienDoHoc;
import org.springframework.stereotype.Service;
import repository.TienDoHocRepository;
import service.ITienDoHocService;

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
    public TienDoHoc findById(int id) {
        return tienDoHocRepository.findById(id).orElse(null);
    }

    @Override
    public TienDoHoc save(TienDoHoc tienDoHoc) {
        return tienDoHocRepository.save(tienDoHoc);
    }

    @Override
    public void deleteById(int id) {
        tienDoHocRepository.deleteById(id);
    }
}
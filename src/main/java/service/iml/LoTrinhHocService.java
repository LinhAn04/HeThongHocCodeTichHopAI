package service.iml;

import model.LoTrinhHoc;
import org.springframework.stereotype.Service;
import repository.LoTrinhHocRepository;
import service.ILoTrinhHocService;

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
    public LoTrinhHoc findById(int id) {
        return loTrinhHocRepository.findById(id).orElse(null);
    }

    @Override
    public LoTrinhHoc save(LoTrinhHoc loTrinhHoc) {
        return loTrinhHocRepository.save(loTrinhHoc);
    }

    @Override
    public void deleteById(int id) {
        loTrinhHocRepository.deleteById(id);
    }
}

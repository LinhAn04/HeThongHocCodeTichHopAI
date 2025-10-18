package service.iml;

import model.BaiHoc;
import org.springframework.stereotype.Service;
import repository.BaiHocRepository;
import service.IBaiHocService;
import java.util.List;

@Service
public class BaiHocService implements IBaiHocService {
    private final BaiHocRepository baiHocRepository;

    public BaiHocService(BaiHocRepository baiHocRepository) {
        this.baiHocRepository = baiHocRepository;
    }

    @Override
    public List<BaiHoc> findAll() {
        return baiHocRepository.findAll();
    }

    @Override
    public BaiHoc findById(int id) {
        return baiHocRepository.findById(id).orElse(null);
    }

    @Override
    public BaiHoc save(BaiHoc baiHoc) {
        return baiHocRepository.save(baiHoc);
    }

    @Override
    public void deleteById(int id) {
        baiHocRepository.deleteById(id);
    }
}
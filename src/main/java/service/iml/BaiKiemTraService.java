package service.iml;

import model.BaiKiemTra;
import org.springframework.stereotype.Service;
import repository.BaiKiemTraRepository;
import service.IBaiKiemTraService;

import java.util.List;

@Service
public class BaiKiemTraService implements IBaiKiemTraService {
    private final BaiKiemTraRepository baiKiemTraRepository;

    public BaiKiemTraService(BaiKiemTraRepository baiKiemTraRepository) {
        this.baiKiemTraRepository = baiKiemTraRepository;
    }

    @Override
    public List<BaiKiemTra> findAll() {
        return baiKiemTraRepository.findAll();
    }

    @Override
    public BaiKiemTra findById(int id) {
        return baiKiemTraRepository.findById(id).orElse(null);
    }

    @Override
    public BaiKiemTra save(BaiKiemTra baiKiemTra) {
        return baiKiemTraRepository.save(baiKiemTra);
    }

    @Override
    public void deleteById(int id) {
        baiKiemTraRepository.deleteById(id);
    }
}

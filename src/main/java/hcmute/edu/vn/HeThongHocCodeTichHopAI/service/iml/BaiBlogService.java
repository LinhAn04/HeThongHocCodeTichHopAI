package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.BaiBlogRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IBaiBlogService;

import java.util.List;

@Service
public class BaiBlogService implements IBaiBlogService {
    private final BaiBlogRepository baiBlogRepository;

    public BaiBlogService(BaiBlogRepository baiBlogRepository) {
        this.baiBlogRepository = baiBlogRepository;
    }

    @Override
    public List<BaiBlog> findAll() {
        return baiBlogRepository.findAll();
    }

    @Override
    public BaiBlog findById(String id) {
        return baiBlogRepository.findById(id).orElse(null);
    }

    @Override
    public BaiBlog save(BaiBlog baiBlog) {
        return baiBlogRepository.save(baiBlog);
    }

    @Override
    public void deleteById(String id) {
        baiBlogRepository.deleteById(id);
    }
}

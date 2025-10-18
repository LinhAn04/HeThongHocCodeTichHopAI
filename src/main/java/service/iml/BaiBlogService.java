package service.iml;

import model.BaiBlog;
import org.springframework.stereotype.Service;
import repository.BaiBlogRepository;
import service.IBaiBlogService;

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
    public BaiBlog findById(int id) {
        return baiBlogRepository.findById(id).orElse(null);
    }

    @Override
    public BaiBlog save(BaiBlog baiBlog) {
        return baiBlogRepository.save(baiBlog);
    }

    @Override
    public void deleteById(int id) {
        baiBlogRepository.deleteById(id);
    }
}

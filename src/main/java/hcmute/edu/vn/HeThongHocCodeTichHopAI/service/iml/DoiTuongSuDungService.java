package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;

import java.util.List;

@Service
public class DoiTuongSuDungService implements IDoiTuongSuDungService {

    private final DoiTuongSuDungRepository repo;

    public DoiTuongSuDungService(DoiTuongSuDungRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<DoiTuongSuDung> findAll() {
        return repo.findAll();
    }

    @Override
    public DoiTuongSuDung findById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public DoiTuongSuDung save(DoiTuongSuDung d) {
        return repo.save(d);
    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    public DoiTuongSuDung findByEmail(String email) {
        return repo.findByEmail(email);
    }
}
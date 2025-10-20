package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;

import java.util.List;

@Service
public class DoiTuongSuDungService implements IDoiTuongSuDungService {
    private final DoiTuongSuDungRepository doiTuongSuDungRepository;

    public DoiTuongSuDungService(DoiTuongSuDungRepository doiTuongSuDungRepository) {
        this.doiTuongSuDungRepository = doiTuongSuDungRepository;
    }

    @Override
    public List<DoiTuongSuDung> findAll() {
        return doiTuongSuDungRepository.findAll();
    }

    @Override
    public DoiTuongSuDung findById(String id) {
        return doiTuongSuDungRepository.findById(id).orElse(null);
    }

    @Override
    public DoiTuongSuDung save(DoiTuongSuDung doiTuongSuDung) {
        return doiTuongSuDungRepository.save(doiTuongSuDung);
    }

    @Override
    public void deleteById(String id) {
        doiTuongSuDungRepository.deleteById(id);
    }
}
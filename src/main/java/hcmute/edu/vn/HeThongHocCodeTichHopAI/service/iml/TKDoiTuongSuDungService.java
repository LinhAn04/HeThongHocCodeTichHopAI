package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import org.springframework.stereotype.Service;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITKDoiTuongSuDungService;

import java.util.List;

@Service
public class TKDoiTuongSuDungService implements ITKDoiTuongSuDungService {
    private final TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository;

    public TKDoiTuongSuDungService(TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository) {
        this.tkDoiTuongSuDungRepository = tkDoiTuongSuDungRepository;
    }

    @Override
    public List<TKDoiTuongSuDung> findAll() {
        return tkDoiTuongSuDungRepository.findAll();
    }

    @Override
    public TKDoiTuongSuDung findById(String id) {
        return tkDoiTuongSuDungRepository.findById(id).orElse(null);
    }

    @Override
    public TKDoiTuongSuDung save(TKDoiTuongSuDung tkDoiTuongSuDung) {
        return tkDoiTuongSuDungRepository.save(tkDoiTuongSuDung);
    }

    @Override
    public void deleteById(String id) {
        tkDoiTuongSuDungRepository.deleteById(id);
    }
}

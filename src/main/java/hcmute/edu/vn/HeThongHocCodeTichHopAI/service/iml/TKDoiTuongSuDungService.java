package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITKDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TKDoiTuongSuDungService implements ITKDoiTuongSuDungService {

    private final TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository;
    private final DoiTuongSuDungRepository doiTuongSuDungRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public TKDoiTuongSuDungService(
            TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository,
            DoiTuongSuDungRepository doiTuongSuDungRepository,
            EmailService emailService) {
        this.tkDoiTuongSuDungRepository = tkDoiTuongSuDungRepository;
        this.doiTuongSuDungRepository = doiTuongSuDungRepository;
        this.emailService = emailService;
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
    public TKDoiTuongSuDung save(TKDoiTuongSuDung tk) {
        return tkDoiTuongSuDungRepository.save(tk);
    }

    @Override
    public void deleteById(String id) {
        tkDoiTuongSuDungRepository.deleteById(id);
    }
}

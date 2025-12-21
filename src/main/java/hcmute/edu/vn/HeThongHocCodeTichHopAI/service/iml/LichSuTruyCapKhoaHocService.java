package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.LichSuTruyCapKhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ILichSuTruyCapKhoaHocService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LichSuTruyCapKhoaHocService implements ILichSuTruyCapKhoaHocService {
    private final LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository;

    public LichSuTruyCapKhoaHocService(LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository) {
        this.lichSuTruyCapKhoaHocRepository = lichSuTruyCapKhoaHocRepository;
    }

    @Override
    public List<LichSuTruyCapKhoaHoc> findAll() {
        return lichSuTruyCapKhoaHocRepository.findAll();
    }

    @Override
    public LichSuTruyCapKhoaHoc findById(String id) {
        return lichSuTruyCapKhoaHocRepository.findById(id).orElse(null);
    }

    @Override
    public LichSuTruyCapKhoaHoc save(LichSuTruyCapKhoaHoc lichSuTruyCapKhoaHoc) {
        return lichSuTruyCapKhoaHocRepository.save(lichSuTruyCapKhoaHoc);
    }

    @Override
    public void deleteById(String id) {
        lichSuTruyCapKhoaHocRepository.deleteById(id);
    }

    @Override
    public void saveOrUpdate(DoiTuongSuDung user, KhoaHoc khoaHoc) {

        if (user == null || khoaHoc == null) return;

        LichSuTruyCapKhoaHoc ls =
                lichSuTruyCapKhoaHocRepository.findByUser_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                    user.getIdDoiTuong(),
                    khoaHoc.getIdKhoaHoc()
                );

        if (ls == null) {
            ls = new LichSuTruyCapKhoaHoc();
            ls.setUser(user);
            ls.setKhoaHoc(khoaHoc);
        }

        ls.setThoiGianTruyCapGanNhat(LocalDateTime.now());

        lichSuTruyCapKhoaHocRepository.save(ls);
    }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DangKyKhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDangKyKhoaHocService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangKyKhoaHocService implements IDangKyKhoaHocService {
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;

    public DangKyKhoaHocService(DangKyKhoaHocRepository dangKyKhoaHocRepository) {
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
    }

    @Override
    public List<DangKyKhoaHoc> findAll() {
        return dangKyKhoaHocRepository.findAll();
    }

    @Override
    public DangKyKhoaHoc findById(String id) {
        return dangKyKhoaHocRepository.findById(id).orElse(null);
    }

    @Override
    public DangKyKhoaHoc save(DangKyKhoaHoc dangKyKhoaHoc) {
        return dangKyKhoaHocRepository.save(dangKyKhoaHoc);
    }

    @Override
    public void deleteById(String id) {
        dangKyKhoaHocRepository.deleteById(id);
    }

    @Override
    public DangKyKhoaHoc findByUserAndCourse(
            DoiTuongSuDung user,
            KhoaHoc khoaHoc) {

        if (user == null || khoaHoc == null) {
            return null;
        }

        return dangKyKhoaHocRepository
                .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                        user.getIdDoiTuong(),
                        khoaHoc.getIdKhoaHoc()
                );
    }
}

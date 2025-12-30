package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TrangThaiKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DangKyKhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.KhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDangKyKhoaHocService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DangKyKhoaHocService implements IDangKyKhoaHocService {
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    private final KhoaHocRepository khoaHocRepository;

    public DangKyKhoaHocService(DangKyKhoaHocRepository dangKyKhoaHocRepository,
                                KhoaHocRepository khoaHocRepository) {
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
        this.khoaHocRepository = khoaHocRepository;
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
                )
                .orElse(null);
    }

    @Override
    public boolean daDangKy(String userId, String courseId) {
        return dangKyKhoaHocRepository
                .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(userId, courseId)
                .isPresent();
    }

    @Override
    public DangKyKhoaHoc dangKyKhoaHoc(
            DoiTuongSuDung user,
            KhoaHoc khoaHoc
    ) {

        if (daDangKy(user.getIdDoiTuong(), khoaHoc.getIdKhoaHoc())) {
            throw new RuntimeException("Đã đăng ký khóa học");
        }

        DangKyKhoaHoc dk = new DangKyKhoaHoc();
        dk.setDoiTuongSuDung(user);
        dk.setKhoaHoc(khoaHoc);
        dk.setTrangThaiKhoaHoc(TrangThaiKhoaHoc.DANGHOC);
        dk.setThoiGianDangKy(LocalDateTime.now());
        dk.setThoiGianKetThuc(null);

        if (!Boolean.TRUE.equals(khoaHoc.getHasEnrollment())) {
            khoaHoc.setHasEnrollment(true);
            khoaHocRepository.save(khoaHoc);
        }

        return dangKyKhoaHocRepository.save(dk);
    }

    @Override
    public List<DangKyKhoaHoc> findByUserId(String userId) {
        return dangKyKhoaHocRepository
                .findByNguoiHoc_IdDoiTuong(userId);
    }

    @Override
    public List<String> findCourseIdsByUser(String userId) {

        return dangKyKhoaHocRepository
                .findByNguoiHoc_IdDoiTuong(userId)
                .stream()
                .map(dk -> dk.getKhoaHoc().getIdKhoaHoc())
                .toList();
    }
}

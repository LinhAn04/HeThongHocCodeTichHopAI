package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DangKyKhoaHocRepository extends MongoRepository<DangKyKhoaHoc, String> {
    DangKyKhoaHoc findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(String userId, String courseId);
}

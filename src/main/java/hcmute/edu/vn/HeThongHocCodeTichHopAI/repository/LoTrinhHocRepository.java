package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoTrinhHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoTrinhHocRepository extends MongoRepository<LoTrinhHoc, String> {
    LoTrinhHoc findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(String userId, String courseId);
}

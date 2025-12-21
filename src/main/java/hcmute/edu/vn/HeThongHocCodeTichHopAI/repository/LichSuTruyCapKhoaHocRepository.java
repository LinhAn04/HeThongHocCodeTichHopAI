package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LichSuTruyCapKhoaHocRepository extends MongoRepository<LichSuTruyCapKhoaHoc, String> {
    LichSuTruyCapKhoaHoc findByUser_IdDoiTuongAndKhoaHoc_IdKhoaHoc(String userId, String courseId);
    List<LichSuTruyCapKhoaHoc> findByUser_IdDoiTuongOrderByThoiGianTruyCapGanNhatDesc(String userId);
}

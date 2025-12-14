package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LichSuTruyCapKhoaHocRepository extends MongoRepository<LichSuTruyCapKhoaHoc, String> {

    Optional<LichSuTruyCapKhoaHoc> findByUserAndKhoaHoc(
            DoiTuongSuDung user,
            KhoaHoc khoaHoc
    );

    List<LichSuTruyCapKhoaHoc> findByUserOrderByThoiGianTruyCapGanNhatDesc(
            DoiTuongSuDung user
    );
}


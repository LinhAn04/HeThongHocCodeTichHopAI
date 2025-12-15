package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DangKyKhoaHocRepository extends MongoRepository<DangKyKhoaHoc, String> {
    DangKyKhoaHoc findByNguoiHocAndKhoaHoc(
            DoiTuongSuDung nguoiHoc,
            KhoaHoc khoaHoc
    );
}

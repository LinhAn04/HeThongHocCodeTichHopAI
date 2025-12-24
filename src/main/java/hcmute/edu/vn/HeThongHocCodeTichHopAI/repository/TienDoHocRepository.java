package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TienDoHoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TienDoHocRepository extends MongoRepository<TienDoHoc, String> {
//    Optional<TienDoHoc> findByNguoiHocAndKhoaHoc(
//            DoiTuongSuDung nguoiHoc,
//            KhoaHoc khoaHoc
//    );
}

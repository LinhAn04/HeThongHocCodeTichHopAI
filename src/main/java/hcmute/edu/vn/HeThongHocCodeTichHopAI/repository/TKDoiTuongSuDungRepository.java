package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TKDoiTuongSuDungRepository extends MongoRepository<TKDoiTuongSuDung, String> {
    Optional<TKDoiTuongSuDung> findByTenDangNhap(String tenDangNhap);
}

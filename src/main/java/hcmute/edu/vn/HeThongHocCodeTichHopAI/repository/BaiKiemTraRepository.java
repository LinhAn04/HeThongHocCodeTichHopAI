package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiKiemTra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BaiKiemTraRepository extends MongoRepository<BaiKiemTra, String> {
    Optional<BaiKiemTra> findByBaiHoc(BaiHoc baiHoc);
    Boolean existsByBaiHoc(BaiHoc baiHoc);
}

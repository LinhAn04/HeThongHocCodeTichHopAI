package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiKiemTra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaiKiemTraRepository extends MongoRepository<BaiKiemTra, String> {
    BaiKiemTra findByBaiHoc(BaiHoc baiHoc);
    Boolean existsByBaiHoc(BaiHoc baiHoc);
}

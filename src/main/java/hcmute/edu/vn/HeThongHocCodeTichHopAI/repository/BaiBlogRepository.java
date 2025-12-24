package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BaiBlogRepository extends MongoRepository<BaiBlog, String> {
    List<BaiBlog> findAllByOrderByThoiGianDangBaiDesc();
}

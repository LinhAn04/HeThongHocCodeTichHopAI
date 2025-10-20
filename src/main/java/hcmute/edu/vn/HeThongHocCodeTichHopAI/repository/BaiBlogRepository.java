package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.BaiBlog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaiBlogRepository extends MongoRepository<BaiBlog, String> {
}

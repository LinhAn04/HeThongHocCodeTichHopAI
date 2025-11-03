package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.EmailVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailVerificationRepository extends MongoRepository<EmailVerification, String> {
}

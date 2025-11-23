package hcmute.edu.vn.HeThongHocCodeTichHopAI.repository;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.EmailVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface EmailVerificationRepository extends MongoRepository<EmailVerification, String> {
    void deleteByExpiredAtBefore(LocalDateTime now);
}

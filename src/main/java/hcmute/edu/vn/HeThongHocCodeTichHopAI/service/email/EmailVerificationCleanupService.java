package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationCleanupService {

    private final EmailVerificationRepository repo;

    @Scheduled(fixedDelay = 60 * 1000) // chạy mỗi 1 phút
    public void cleanupExpiredOtp() {
        repo.deleteByExpiredAtBefore(LocalDateTime.now());
    }
}
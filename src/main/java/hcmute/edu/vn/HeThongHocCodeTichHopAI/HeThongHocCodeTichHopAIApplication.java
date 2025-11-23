package hcmute.edu.vn.HeThongHocCodeTichHopAI;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class HeThongHocCodeTichHopAIApplication {
    public static void main(String[] args) {
        // Load biến môi trường từ file .env
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));

        // Chạy ứng dụng Spring Boot
        SpringApplication.run(HeThongHocCodeTichHopAIApplication.class, args);
    }
}

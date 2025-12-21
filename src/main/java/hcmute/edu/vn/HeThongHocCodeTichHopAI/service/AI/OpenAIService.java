package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.AI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

//    @Value("${openai.api-key}")
    private String apiKey;

    public String ask(String prompt) {

        // dùng WebClient hoặc OkHttp
        // gọi https://api.openai.com/v1/chat/completions
        // model: gpt-4o-mini

        return "AI response";
    }
}
package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.AI;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService implements IOpenAIService {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    @Value("${OPENAI_MODEL}")
    private String model;

    private WebClient webClient;

    @PostConstruct
    void init() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("OPENAI_API_KEY not loaded from .env");
        }

        webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();

        System.out.println("✅ OpenAI key loaded, model = " + model);
    }

    @Override
    public String chat(String systemPrompt, String userPrompt) {

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "temperature", 0.7
        );

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json ->
                        json.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText()
                )
                .block();
    }
}
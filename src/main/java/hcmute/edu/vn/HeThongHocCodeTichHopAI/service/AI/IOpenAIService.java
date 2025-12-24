package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.AI;

public interface IOpenAIService {
    String chat(String systemPrompt, String userPrompt);
}

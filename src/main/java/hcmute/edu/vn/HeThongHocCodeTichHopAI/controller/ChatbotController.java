package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.LoTrinhHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.AI.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final IOpenAIService openAIService;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final IDangKyKhoaHocService dangKyKhoaHocService;
    private final IKhoaHocService khoaHocService;
    private final ITienDoHocService tienDoHocService;
    private final IThongBaoService thongBaoService;
    private final LoTrinhHocRepository loTrinhHocRepository;

    public ChatbotController(IOpenAIService openAIService,
                             IDoiTuongSuDungService doiTuongSuDungService,
                             IDangKyKhoaHocService dangKyKhoaHocService,
                             IKhoaHocService khoaHocService,
                             ITienDoHocService tienDoHocService,
                             IThongBaoService thongBaoService,
                             LoTrinhHocRepository loTrinhHocRepository) {
        this.openAIService = openAIService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocService = dangKyKhoaHocService;
        this.khoaHocService = khoaHocService;
        this.tienDoHocService = tienDoHocService;
        this.thongBaoService = thongBaoService;
        this.loTrinhHocRepository = loTrinhHocRepository;
    }

    // Hàm chatbot trò chuyện bình thường
    @PostMapping("/chat")
    public Map<String, String> chat(
            @RequestBody Map<String, String> req,
            HttpServletRequest request
    ) {
        String message = req.get("message");

        if (message == null || message.isBlank()) {
            return Map.of("reply", "Please enter a message 🙂");
        }

        // Lấy user hiện tại
        String email = (String) request.getSession().getAttribute("email");

        DoiTuongSuDung user;
        if (email != null) {
            user = doiTuongSuDungService.findByEmail(email);
        } else {
            user = null;
        }

        // Lấy danh sách khóa học user đã đăng ký
        List<DangKyKhoaHoc> dangKyList = (user != null)
                ? dangKyKhoaHocService.findByUserId(user.getIdDoiTuong())
                : List.of();

        String courseContext;
        if (dangKyList.isEmpty()) {
            courseContext = "- User has not enrolled in any course yet.";
        } else {
            courseContext = dangKyList.stream().map(dk -> {
                double progress = 0;
                String currentLesson = "Not started yet";

                LoTrinhHoc loTrinh = loTrinhHocRepository
                        .findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                                user.getIdDoiTuong(),
                                dk.getKhoaHoc().getIdKhoaHoc()
                        );

                if (loTrinh != null && loTrinh.getTienDoHoc() != null) {
                    TienDoHoc tienDo = loTrinh.getTienDoHoc();
                    progress = tienDo.getTiLeHoanThanh();

                    if (tienDo.getBaiHocHienTai() != null) {
                        currentLesson = tienDo.getBaiHocHienTai().getTieuDeBaiHoc();
                    }
                }

                return """
📘 **%s**
Status: %s  
Progress: %.0f%%  
Current lesson: %s
""".formatted(
                        dk.getKhoaHoc().getTenKhoaHoc(),
                        dk.getTrangThaiKhoaHoc(),
                        progress,
                        currentLesson
                );
            }).reduce("", String::concat);
        }

        String systemPrompt = """
            You are Codemy AI – a friendly, enthusiastic, and professional learning assistant.
            
            USER PROFILE:
            - Name: %s
            - Role: Student
            - Enrolled courses and learning progress:
            %s
            
            INSTRUCTIONS:
            - Always respond in a warm, motivating tone.
            - Use bullet points, emojis (lightly 😊🚀), and line breaks for readability.
            - If user asks about learning progress, base answers on the data above.
            - If user is stuck, encourage them and give clear next steps.
            - If user has no courses, suggest beginner-friendly courses politely.
            - NEVER mention database or internal system.
            - ALWAYS format responses with line breaks.
            
            OUTPUT FORMAT:
            - Plain text
            - Use \\n for new lines
            """.formatted(
                user != null ? user.getHoTen() : "Guest",
                courseContext
        );

        String reply = openAIService.chat(systemPrompt, message);
        return Map.of("reply", reply);
    }

    // Hàm để chatbot đánh giá code
    @PostMapping("/code-evaluate")
    public CodeEvaluationResponse evaluateCode(
            @RequestBody Map<String, String> req
    ) {
        String problem = req.get("problem");
        String expected = req.get("expected");
        String code = req.get("code");

        if (code == null || code.isBlank()) {
            return new CodeEvaluationResponse(
                    0,
                    "Code is empty.",
                    List.of("Please write a solution before submitting.")
            );
        }

        String systemPrompt = """
        You are Codemy AI, a strict but fair coding evaluator.

        Evaluate the student's code.

        Output STRICTLY in this JSON format:
        {
          "score": number (0-100),
          "evaluation": "short paragraph",
          "improvements": ["item 1", "item 2", "item 3"]
        }

        Rules:
        - Score must be realistic
        - Be constructive and beginner-friendly
        - Improvements must be concrete
        - DO NOT add extra text outside JSON
        """;

        String userPrompt = """
        Problem:
        %s

        Expected output:
        %s

        Student code:
        %s
        """.formatted(problem, expected, code);

        String aiRawResponse = openAIService.chat(systemPrompt, userPrompt);

        // Parse JSON từ AI
        return parseAIResponse(aiRawResponse);
    }

    private CodeEvaluationResponse parseAIResponse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, CodeEvaluationResponse.class);
        } catch (Exception e) {
            // fallback nếu AI trả sai format
            return new CodeEvaluationResponse(
                    60,
                    "Your solution works but needs improvement.",
                    List.of("Improve readability", "Handle edge cases")
            );
        }
    }
}
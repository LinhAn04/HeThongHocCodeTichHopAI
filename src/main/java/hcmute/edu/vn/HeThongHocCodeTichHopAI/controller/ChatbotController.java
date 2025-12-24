package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.AI.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpSession;
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

    public ChatbotController(IOpenAIService openAIService,
                             IDoiTuongSuDungService doiTuongSuDungService,
                             IDangKyKhoaHocService dangKyKhoaHocService) {
        this.openAIService = openAIService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocService = dangKyKhoaHocService;
    }

    /**
     * CHATBOT CHUNG
     * - chatbot.html
     * - popup enroll
     * - popup lesson type 4
     */
    @PostMapping("/chat")
    public Map<String, String> chat(
            @RequestBody Map<String, String> req,
            Principal principal
    ) {
        String message = req.get("message");

        if (message == null || message.isBlank()) {
            return Map.of("reply", "Please enter a message 🙂");
        }

        // Lấy user hiện tại
        DoiTuongSuDung user;
        if (principal != null) {
            user = doiTuongSuDungService.findByEmail(principal.getName());
        } else {
            user = null;
        }

        // Lấy danh sách khóa học user đã đăng ký
        List<DangKyKhoaHoc> dangKyList =
                (user != null)
                        ? dangKyKhoaHocService.findAll().stream()
                        .filter(dk -> dk.getNguoiHoc().getIdDoiTuong()
                                .equals(user.getIdDoiTuong()))
                        .toList()
                        : List.of();

        // Chuẩn hóa context cho AI
        String courseContext =
                dangKyList.isEmpty()
                        ? "- No enrolled courses"
                        : dangKyList.stream()
                        .map(dk ->
                                "- " + dk.getKhoaHoc().getTenKhoaHoc()
                                        + " (" + dk.getTrangThaiKhoaHoc() + ")"
                        )
                        .reduce("", (a, b) -> a + "\n" + b);

        String systemPrompt = """
            You are Codemy AI, an intelligent programming tutor.
            
            User context:
            - Name: %s
            - Enrolled courses:
            %s
            
            Rules:
            - You KNOW the user's enrolled courses.
            - If user asks about learning progress, use the course status.
            - If user has no courses, suggest beginner-friendly courses.
            - Be concise, friendly, and supportive.
            """.formatted(
                                user != null ? user.getHoTen() : "Guest",
                                courseContext
                        );

                        String reply = openAIService.chat(systemPrompt, message);
                        return Map.of("reply", reply);
    }

    /**
     * AI REVIEW CODE – LESSON TYPE 4
     */
    @PostMapping("/code-review")
    public Map<String, String> reviewCode(@RequestBody Map<String, String> req) {

        String problem = req.get("problem");
        String expected = req.get("expected");
        String code = req.get("code");

        if (code == null || code.trim().isEmpty()) {
            return Map.of("feedback", "Code is empty.");
        }

        String userPrompt = """
            Problem:
            %s

            Expected output:
            %s

            Student code:
            %s

            Tasks:
            1. Tell if the solution is correct.
            2. Point out errors (if any).
            3. Suggest improvements.
            """.formatted(problem, expected, code);

        String feedback = openAIService.chat(
                "You are a strict but helpful coding instructor.",
                userPrompt
        );

        return Map.of("feedback", feedback);
    }

    @PostMapping("/enroll-flow")
    public Map<String, Object> enrollFlow(
            @RequestBody Map<String, String> req,
            HttpSession session
    ) {
        String courseName = req.get("courseName");
        String message = req.getOrDefault("message", "").trim();

        EnrollChatState state =
                (EnrollChatState) session.getAttribute("ENROLL_CHAT");

        if (state == null) {
            state = new EnrollChatState();
            state.setStep(EnrollChatStep.INTRO);
            session.setAttribute("ENROLL_CHAT", state);
        }

        switch (state.getStep()) {

            case INTRO -> {
                state.setStep(EnrollChatStep.READY_CONFIRM);
                return Map.of(
                        "reply", openAIService.chat("""
                            You are Codemy AI.
                            
                            Introduce the course "%s" in 1–2 friendly sentences.
                            Then ask:
                            
                            "Are you ready to answer 2 small questions?
                            After that, you can enroll in this course."
                            
                            Use clear paragraphs.
                            """.formatted(courseName), "")
                );
            }

            case READY_CONFIRM -> {
                state.setStep(EnrollChatStep.QUESTION_1);
                state.setQ1Correct("A"); // demo
                return Map.of(
                        "reply", openAIService.chat("""
                            Ask ONE simple multiple-choice question (A/B/C)
                            about basic knowledge needed for "%s".
                            
                            Format clearly with line breaks.
                            """.formatted(courseName), "")
                );
            }

            case QUESTION_1 -> {
                boolean correct = message.equalsIgnoreCase(state.getQ1Correct());
                state.setStep(EnrollChatStep.QUESTION_2);
                state.setQ2Correct("B"); // demo

                return Map.of(
                        "reply", openAIService.chat("""
                            The student answered "%s".
                            Correct answer is "%s".
                            
                            Explain briefly why it is %s.
                            Then ask the SECOND question (A/B/C).
                            """.formatted(
                                    message,
                                    state.getQ1Correct(),
                                    correct ? "correct" : "incorrect"
                            ), "")
                );
            }

            case QUESTION_2 -> {
                boolean correct = message.equalsIgnoreCase(state.getQ2Correct());
                state.setStep(EnrollChatStep.DONE);
                session.removeAttribute("ENROLL_CHAT");

                return Map.of(
                        "reply", openAIService.chat("""
                            The student answered "%s".
                            Correct answer is "%s".
                            
                            Explain briefly.
                            Encourage the student.
                            """.formatted(message, state.getQ2Correct()), ""),
                        "showEnroll", true
                );
            }
            default -> {
                return Map.of("reply", "Session ended.");
            }
        }
    }
}
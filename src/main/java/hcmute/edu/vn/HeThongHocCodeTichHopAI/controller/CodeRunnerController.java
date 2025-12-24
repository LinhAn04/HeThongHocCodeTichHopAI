package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.CodeExecutionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeRunnerController {

    private final CodeExecutionService codeExecutionService;

    public CodeRunnerController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/run")
    public Map<String, String> runCode(@RequestBody Map<String, String> req) {
        String code = req.get("code");

        if (code == null || code.isBlank()) {
            return Map.of("output", "Code is empty");
        }

        String output = codeExecutionService.runCpp(code);

        return Map.of("output", output);
    }
}


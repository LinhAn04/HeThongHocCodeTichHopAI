package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

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
    public ExecutionResult runCode(@RequestBody Map<String, String> req) {

        String code = req.get("code");
        String input = req.get("input");

        if (code == null || code.isBlank()) {
            return new ExecutionResult(false, "", "Code is empty");
        }

        return codeExecutionService.runCpp(code, input);
    }
}


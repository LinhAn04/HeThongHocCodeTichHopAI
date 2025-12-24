package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CodeExecutionService {

    public String runCpp(String code) {
        try {
            Path tempDir = Files.createTempDirectory("cpp-run");
            Path cppFile = tempDir.resolve("main.cpp");

            Files.writeString(cppFile, code);

            // compile
            Process compile = new ProcessBuilder(
                    "g++", cppFile.toString(), "-o", "main"
            ).directory(tempDir.toFile()).start();

            compile.waitFor();

            if (compile.exitValue() != 0) {
                return "Compilation error";
            }

            // run
            Process run = new ProcessBuilder("./main")
                    .directory(tempDir.toFile())
                    .start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(run.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();

        } catch (Exception e) {
            return "Runtime error: " + e.getMessage();
        }
    }
}


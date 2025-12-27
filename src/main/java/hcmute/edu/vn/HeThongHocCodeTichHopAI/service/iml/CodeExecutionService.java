package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.ExecutionResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutionService {

    public ExecutionResult runCpp(String code, String input) {
        try {
            Path tempDir = Files.createTempDirectory("cpp-run");
            Path cppFile = tempDir.resolve("main.cpp");

            Files.writeString(cppFile, code);

            // 1. Compile
            String gppPath = "C:\\msys64\\mingw64\\bin\\g++.exe";

            Process compile = new ProcessBuilder(
                    gppPath, "main.cpp", "-o", "main.exe"
            )
                    .directory(tempDir.toFile())
                    .start();

            boolean compiled = compile.waitFor(5, TimeUnit.SECONDS);
            if (!compiled || compile.exitValue() != 0) {
                return new ExecutionResult(
                        false,
                        readStream(compile.getErrorStream()),
                        "Compilation Error"
                );
            }

            // 2. Run
            Process run = new ProcessBuilder("main.exe")
                    .directory(tempDir.toFile())
                    .start();

            // write input (cin)
            if (input != null && !input.isBlank()) {
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(run.getOutputStream()));
                writer.write(input);
                writer.flush();
                writer.close();
            }

            boolean finished = run.waitFor(3, TimeUnit.SECONDS);
            if (!finished) {
                run.destroyForcibly();
                return new ExecutionResult(
                        false,
                        "",
                        "Time Limit Exceeded"
                );
            }

            String output = readStream(run.getInputStream());
            String error = readStream(run.getErrorStream());

            return new ExecutionResult(true, output, error);

        } catch (Exception e) {
            return new ExecutionResult(false, "", "Runtime Error: " + e.getMessage());
        }
    }

    private String readStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}

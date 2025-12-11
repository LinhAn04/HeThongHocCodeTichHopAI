package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.GioiTinh;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ITKDoiTuongSuDungService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerProfileController {

    private final IDoiTuongSuDungService doiTuongService;
    private final ITKDoiTuongSuDungService tkDoiTuongSuDungService;

    public CustomerProfileController(IDoiTuongSuDungService doiTuongService,
                                     ITKDoiTuongSuDungService tkDoiTuongSuDungService) {
        this.doiTuongService = doiTuongService;
        this.tkDoiTuongSuDungService = tkDoiTuongSuDungService;
    }

    @GetMapping("/account-setting")
    public ModelAndView viewProfile(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        System.out.println("Session email = " + email);

        ModelAndView mv = new ModelAndView("customer_account_setting");

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        System.out.println("User loaded = " + user);

        mv.addObject("user", user);
        mv.addObject("activeMenu", "profile");
        return mv;
    }

    @PutMapping("/update-profile")
    @ResponseBody
    public ResponseEntity<?> updateProfile(
            HttpServletRequest request,
            @RequestBody Map<String, String> body
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "error", "Session expired"
            ));
        }

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "error", "User not found"
            ));
        }

        String hoTen = body.get("hoTen");
        String soDienThoai = body.get("soDienThoai");
        String diaChi = body.get("diaChi");
        String ngaySinh = body.get("ngaySinh");
        String gioiTinh = body.get("gioiTinh");

        if (hoTen != null && !hoTen.isBlank())
            user.setHoTen(hoTen);

        if (soDienThoai != null)
            user.setSoDienThoai(soDienThoai);

        if (diaChi != null)
            user.setDiaChi(diaChi);

        if (ngaySinh != null && !ngaySinh.isBlank()) {
            try {
                user.setNgaySinh(LocalDate.parse(ngaySinh));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "error", "Invalid birthday format"
                ));
            }
        }

        if (gioiTinh != null) {
            try {
                user.setGioiTinh(GioiTinh.valueOf(gioiTinh));
            } catch (Exception ignored) {}
        }

        doiTuongService.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Profile updated successfully!"
        ));
    }

    @PutMapping(value = "/update-json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> updateProfileJson(HttpServletRequest request,
                                               @RequestBody DoiTuongSuDung data) {

        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            return ResponseEntity.badRequest().body("Session expired");
        }

        DoiTuongSuDung user = doiTuongService.findByEmail(email);

        if (data.getHoTen() != null) user.setHoTen(data.getHoTen());
        if (data.getDiaChi() != null) user.setDiaChi(data.getDiaChi());
        if (data.getSoDienThoai() != null) user.setSoDienThoai(data.getSoDienThoai());
        if (data.getGioiTinh() != null) user.setGioiTinh(data.getGioiTinh());

        doiTuongService.save(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/avatar")
    @ResponseBody
    public ResponseEntity<?> uploadAvatar(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String email = (String) request.getSession().getAttribute("email");
            DoiTuongSuDung user = doiTuongService.findByEmail(email);
            if (user == null)
                return ResponseEntity.status(404).body(Map.of("error", "User not found"));

            if (file.isEmpty())
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));

            // Folder upload
            String uploadDir = "uploads/avatars/";
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            // Xóa file cũ
            if (user.getAvatar() != null) {
                File old = new File("." + user.getAvatar());
                if (old.exists()) old.delete();
            }

            // Extension
            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            // Tên file theo email
            String prefix = email.substring(0, email.indexOf("@"));
            String newFileName = prefix + "_avatar" + ext;

            // Lưu file
            Path path = Paths.get(uploadDir + newFileName);
            Files.write(path, file.getBytes());

            // Update DB
            user.setAvatar("/uploads/avatars/" + newFileName);
            doiTuongService.save(user);

            // Update session
            request.getSession().setAttribute("user", user);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "avatar", path
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/avatar")
    @ResponseBody
    public ResponseEntity<?> removeAvatar(HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        if (user == null)
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));

        // Xóa file vật lý
        if (user.getAvatar() != null) {
            File old = new File("uploads" + user.getAvatar().replace("/uploads", ""));
            if (old.exists()) old.delete();
        }

        // Xóa avatar
        user.setAvatar(null);
        doiTuongService.save(user);

        // Update session
        request.getSession().setAttribute("user", user);

        return ResponseEntity.ok(Map.of("success", true));
    }

    @PutMapping("/change-password")
    @ResponseBody
    public ResponseEntity<?> updatePassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        String email = (String) request.getSession().getAttribute("email");

        if (email == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        if (user == null)
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));

        TKDoiTuongSuDung tk = user.getTaiKhoan();
        if (tk == null)
            return ResponseEntity.status(404).body(Map.of("error", "Account not found"));

        String newPass = body.get("newPassword");
        if (newPass == null || newPass.length() < 6)
            return ResponseEntity.badRequest().body(Map.of("error",
                    "Password must be at least 8 characters long, contain at least one uppercase letter, " +
                            "one number, and one special character"));
        tk.setMatKhau(newPass);
        tkDoiTuongSuDungService.save(tk);

        return ResponseEntity.ok(Map.of("success", true, "message", "Password updated"));
    }

    // chatbot
    @GetMapping("/chatbot")
    public ModelAndView chatbotPage(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return new ModelAndView("redirect:/login");
        }

        DoiTuongSuDung user = doiTuongService.findByEmail(email);

        ModelAndView mv = new ModelAndView("chatbot");
        mv.addObject("user", user);
        mv.addObject("activeMenu", "chatbot");
        return mv;
    }

    private String generateBotReply(String message) {
        message = message.toLowerCase();

        if (message.contains("hello") || message.contains("hi")) {
            return "Hello! How can I help you today? 😊";
        }

        if (message.contains("course") || message.contains("learn")) {
            return "You can find your courses in the *My Courses* section in the sidebar.";
        }

        if (message.contains("profile")) {
            return "You can update your profile in the *Account → Profile Info* section.";
        }

        if (message.contains("error")) {
            return "If you encounter any errors, please describe them and I’ll do my best to assist!";
        }

        // Default reply
        return "You said: " + message;
    }

    @PutMapping("/chatbot")
    @ResponseBody
    public ResponseEntity<?> chatbot(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Not logged in"
            ));
        }

        String userMessage = body.get("message");

        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Message is empty"
            ));
        }

        //
        String botReply = generateBotReply(userMessage);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "reply", botReply
        ));
    }
}

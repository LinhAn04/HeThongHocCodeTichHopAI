package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.GioiTinh;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
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

    public CustomerProfileController(IDoiTuongSuDungService doiTuongService) {
        this.doiTuongService = doiTuongService;
    }

    @GetMapping("/account-setting")
    public ModelAndView viewProfile(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        System.out.println("Session email = " + email);

        ModelAndView mv = new ModelAndView("customer_account_setting");

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        System.out.println("User loaded = " + user);

        mv.addObject("user", user);
        return mv;
    }

    @PutMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> updateProfile(
            HttpServletRequest request,
            @RequestBody Map<String, String> body
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        if (user == null)
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));

        // --- VALIDATION ---
        String hoTen = body.get("hoTen");
        if (hoTen == null || hoTen.trim().length() < 3)
            return ResponseEntity.badRequest().body(Map.of("error", "Full name must be at least 3 characters"));

        String phone = body.get("soDienThoai");
        if (phone != null && !phone.matches("\\d{9,11}"))
            return ResponseEntity.badRequest().body(Map.of("error", "Phone must contain 9–11 digits"));

        String birthday = body.get("ngaySinh");
        if (birthday != null && !birthday.isBlank()) {
            LocalDate date = LocalDate.parse(birthday);
            if (date.isAfter(LocalDate.now()))
                return ResponseEntity.badRequest().body(Map.of("error", "Birthday cannot be in the future"));
            user.setNgaySinh(date.atStartOfDay());
        }

        String gioiTinh = body.get("gioiTinh");
        if (gioiTinh != null && !gioiTinh.isBlank()) {
            try {
                user.setGioiTinh(GioiTinh.valueOf(gioiTinh.toUpperCase()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid gender"));
            }
        }

        // --- UPDATE ---
        user.setHoTen(hoTen);
        user.setSoDienThoai(body.get("soDienThoai"));
        user.setDiaChi(body.get("diaChi"));

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
            if (email == null)
                return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

            DoiTuongSuDung user = doiTuongService.findByEmail(email);
            if (user == null)
                return ResponseEntity.status(404).body(Map.of("error", "User not found"));

            if (file.isEmpty())
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));

            // --- TẠO FOLDER ---
            String uploadDir = "src/main/resources/static/uploads/avatars/";
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            // --- XÓA AVATAR CŨ ---
            if (user.getAvatar() != null && !user.getAvatar().isBlank()) {
                File oldFile = new File("src/main/resources/static" + user.getAvatar());
                if (oldFile.exists()) oldFile.delete();
            }

            // --- TẠO TÊN FILE MỚI ---
            String extension = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            String newFileName = user.getIdDoiTuong() + "_" + System.currentTimeMillis() + extension;

            Path filePath = Paths.get(uploadDir + newFileName);
            Files.write(filePath, file.getBytes());

            // --- LƯU VÀO DB ---
            user.setAvatar("/uploads/avatars/" + newFileName);
            doiTuongService.save(user);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "avatar", user.getAvatar()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Upload failed",
                    "details", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/avatar")
    @ResponseBody
    public ResponseEntity<?> removeAvatar(HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        DoiTuongSuDung user = doiTuongService.findByEmail(email);
        if (user == null)
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));

        if (user.getAvatar() != null) {
            File oldFile = new File("src/main/resources/static" + user.getAvatar());
            if (oldFile.exists()) oldFile.delete();
            user.setAvatar(null);
            doiTuongService.save(user);
        }

        return ResponseEntity.ok(Map.of("success", true));
    }
}

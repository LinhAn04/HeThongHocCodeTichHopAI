package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IHoaDonService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

    private final KhoaHocRepository khoaHocRepository;
    private final DangKyKhoaHocService dangKyService;
    private final EmailService emailService;
    private final IDoiTuongSuDungService doiTuongSuDungService;

    public EnrollController(KhoaHocRepository khoaHocRepository,
                            DangKyKhoaHocService dangKyService,
                            EmailService emailService,
                            IDoiTuongSuDungService doiTuongSuDungService) {

        this.khoaHocRepository = khoaHocRepository;
        this.dangKyService = dangKyService;
        this.emailService = emailService;
        this.doiTuongSuDungService = doiTuongSuDungService;
    }

    // Trang đăng ký
    @GetMapping("/{courseId}")
    public String enrollPage(
            @PathVariable String courseId,
            Model model,
            HttpServletRequest request
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);

        KhoaHoc khoaHoc = khoaHocRepository.findById(courseId).orElseThrow();

        model.addAttribute("course", khoaHoc);
        model.addAttribute("user", user);

        return "enroll";
    }


    // Thanh toán MoMo (mock)
    @PostMapping("/payment")
    public String momoPayment(
            @RequestParam String courseId,
            HttpServletRequest request
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        return "redirect:/enroll/momo?courseId=" + courseId;
    }

    // Trang QR MoMo
    @GetMapping("/momo")
    public String momoPage(
            @RequestParam String courseId,
            Model model
    ) {
        model.addAttribute("courseId", courseId);
        return "momo";
    }

    // Callback thanh toán thành công
    @PostMapping("/success")
    public String paymentSuccess(
            @RequestParam String courseId,
            HttpServletRequest request
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);

        KhoaHoc khoaHoc = khoaHocRepository
                .findById(courseId)
                .orElseThrow();

        dangKyService.dangKyKhoaHoc(user, khoaHoc);

        emailService.sendEnrollSuccessEmail(
                user.getEmail(),
                user.getHoTen(),
                khoaHoc.getTenKhoaHoc()
        );

        return "redirect:/course/" + courseId;
    }

}

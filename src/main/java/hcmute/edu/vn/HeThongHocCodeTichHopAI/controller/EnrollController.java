package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.KhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.EmailService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.DangKyKhoaHocService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml.MomoPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final MomoPaymentService momoPaymentService;

    public EnrollController(
            KhoaHocRepository khoaHocRepository,
            DangKyKhoaHocService dangKyService,
            EmailService emailService,
            IDoiTuongSuDungService doiTuongSuDungService,
            MomoPaymentService momoPaymentService
    ) {
        this.khoaHocRepository = khoaHocRepository;
        this.dangKyService = dangKyService;
        this.emailService = emailService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.momoPaymentService = momoPaymentService;
    }

    @GetMapping("/{courseId}")
    public String enrollPage(
            @PathVariable String courseId,
            Model model,
            HttpServletRequest request
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/login";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        KhoaHoc course = khoaHocRepository.findById(courseId).orElseThrow();

        // Đã đăng ký rồi → không cho thanh toán lại
        if (dangKyService.daDangKy(user.getIdDoiTuong(), course.getIdKhoaHoc())) {
            return "redirect:/course/" + courseId;
        }

        model.addAttribute("user", user);
        model.addAttribute("course", course);
        return "payment/enroll";
    }

    @PostMapping("/payment")
    public String createPayment(
            @RequestParam String courseId,
            HttpServletRequest request, Model model
    ) throws Exception {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/login";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        KhoaHoc course = khoaHocRepository.findById(courseId).orElseThrow();

        if (dangKyService.daDangKy(user.getIdDoiTuong(), course.getIdKhoaHoc())) {
            return "redirect:/course/" + courseId;
        }

        String payUrl = momoPaymentService.createPayment(
                courseId,
                course.getGiaBan()
        );

        model.addAttribute("user", user);
        return "redirect:" + payUrl;
    }

    @GetMapping("/momo/return")
    public String momoReturn(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) Integer resultCode,
            HttpServletRequest request,
            Model model,
            HttpSession session
    ) {

        // Thanh toán fail / hết hạn
        if (resultCode == null || resultCode != 0 || orderId == null) {
            model.addAttribute("error",
                    "Giao dịch không thành công hoặc đã hết hạn. Vui lòng thanh toán lại.");
            return "redirect:/courses";
        }

        // orderId = COURSE_{courseId}_{timestamp}
        String[] parts = orderId.split("_");
        if (parts.length < 3) {
            return "redirect:/courses";
        }

        String courseId = parts[1];

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/login";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        KhoaHoc course = khoaHocRepository.findById(courseId).orElseThrow();

        // 🔒 Chống callback nhiều lần
        if (!dangKyService.daDangKy(user.getIdDoiTuong(), course.getIdKhoaHoc())) {
            dangKyService.dangKyKhoaHoc(user, course);

            emailService.sendEnrollSuccessEmail(
                    user.getEmail(),
                    user.getHoTen(),
                    course.getTenKhoaHoc()
            );
        }

        model.addAttribute("user", user);
        return "redirect:/course/" + courseId;
    }
}

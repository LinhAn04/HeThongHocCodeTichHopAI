package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/course/{courseId}/momo")
    public String payWithMomo(@PathVariable String courseId,
                              HttpServletRequest request) {

        // TODO: gọi API MoMo tạo payment URL
        String paymentUrl = "https://momo.fake/pay";

        return "redirect:" + paymentUrl;
    }

    @PostMapping("/course/{courseId}/bank")
    public String payWithBank(@PathVariable String courseId) {

        // TODO: tích hợp VNPay / Napas
        return "redirect:https://bank.fake/pay";
    }

//    @GetMapping("/callback")
//    public String paymentCallback(...) {
//
//        // 1. verify payment
//        // 2. tạo HoaDon
//        // 3. tạo DangKyKhoaHoc (CHUA_HOC)
//        // 4. redirect course detail
//
//        return "redirect:/course/{id}?intent=continue";
//    }
}

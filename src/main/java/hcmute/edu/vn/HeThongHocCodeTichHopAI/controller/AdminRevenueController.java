package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DangKyKhoaHocRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/revenue")
public class AdminRevenueController {

    private final DangKyKhoaHocRepository dangKyRepo;

    public AdminRevenueController(DangKyKhoaHocRepository dangKyRepo) {
        this.dangKyRepo = dangKyRepo;
    }

    @GetMapping
    public String revenue(
            @RequestParam(defaultValue = "month") String type,
            HttpSession session,
            Model model
    ) {
        DoiTuongSuDung user =
                (DoiTuongSuDung) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        LocalDate now = LocalDate.now();

        if ("month".equals(type)) {
            // 12 tháng gần nhất
            for (int i = 11; i >= 0; i--) {
                YearMonth ym = YearMonth.from(now.minusMonths(i));

                LocalDateTime start = ym.atDay(1).atStartOfDay();
                LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);

                Double revenue = dangKyRepo.sumRevenueByTime(start, end);
                if (revenue == null) revenue = 0.0;

                labels.add(ym.getMonthValue() + "/" + ym.getYear());
                values.add(revenue);
            }
        } else {
            // 4 quý năm hiện tại
            int year = now.getYear();

            for (int q = 1; q <= 4; q++) {
                int startMonth = (q - 1) * 3 + 1;

                LocalDateTime start = LocalDate
                        .of(year, startMonth, 1)
                        .atStartOfDay();

                LocalDateTime end = start
                        .plusMonths(3)
                        .minusSeconds(1);

                Double revenue = dangKyRepo.sumRevenueByTime(start, end);
                if (revenue == null) revenue = 0.0;

                labels.add("Q" + q + "/" + year);
                values.add(revenue);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("labels", labels);
        model.addAttribute("values", values);
        model.addAttribute("type", type);
        model.addAttribute("activeMenu", "revenue");

        return "admin/view_revenue/revenue";
    }
}


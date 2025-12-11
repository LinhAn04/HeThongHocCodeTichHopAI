package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IKhoaHocService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/courses")
public class CoursePageController {

    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;

    public CoursePageController(
            IKhoaHocService khoaHocService,
            IDoiTuongSuDungService doiTuongSuDungService) {
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
    }

    @GetMapping
    public ModelAndView viewCourses(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("courses");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);

        mv.addObject("loggedIn", loggedIn);

        // Nếu đã đăng nhập → lấy thông tin user đưa vào header
        if (loggedIn) {
            DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
            mv.addObject("user", user);
        }

        mv.addObject("courses", khoaHocService.findAll());
        mv.addObject("bannerCourses", khoaHocService.findAll());
        mv.addObject("activeMenu", "courses");

        return mv;
    }
}


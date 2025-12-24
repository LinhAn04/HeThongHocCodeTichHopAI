package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/my-courses")
public class MyCourseController {

    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final IDangKyKhoaHocService dangKyKhoaHocService;

    public MyCourseController(
            IDoiTuongSuDungService doiTuongSuDungService,
            IDangKyKhoaHocService dangKyKhoaHocService) {
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocService = dangKyKhoaHocService;
    }

    @GetMapping
    public ModelAndView myCourses(HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            return new ModelAndView("redirect:/login");
        }

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);

        List<DangKyKhoaHoc> enrollments =
                dangKyKhoaHocService.findByUserId(
                        user.getIdDoiTuong()
                );

        ModelAndView mv = new ModelAndView("my_courses");
        mv.addObject("loggedIn", true);
        mv.addObject("user", user);
        mv.addObject("enrollments", enrollments);
        mv.addObject("activeMenu", "mycourses");

        return mv;
    }
}


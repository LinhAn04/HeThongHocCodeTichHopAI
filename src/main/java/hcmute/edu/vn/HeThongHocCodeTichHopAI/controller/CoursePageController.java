package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/courses")
public class CoursePageController {

    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final IDangKyKhoaHocService dangKyKhoaHocService;
    private final LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository;

    public CoursePageController(
            IKhoaHocService khoaHocService,
            IDoiTuongSuDungService doiTuongSuDungService,
            IDangKyKhoaHocService dangKyKhoaHocService,
            LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository) {
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocService = dangKyKhoaHocService;
        this.lichSuTruyCapKhoaHocRepository = lichSuTruyCapKhoaHocRepository;
    }

    @GetMapping
    public ModelAndView viewCourses(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("courses");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        mv.addObject("loggedIn", loggedIn);

        DoiTuongSuDung user = null;
        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            mv.addObject("user", user);
        }

        List<KhoaHoc> courses = khoaHocService.findAll();
        mv.addObject("courses", courses);
        mv.addObject("bannerCourses", courses);
        mv.addObject("activeMenu", "courses");

        Map<String, TrangThaiKhoaHoc> courseStatusMap = new HashMap<>();

        if (loggedIn) {
            for (KhoaHoc kh : courses) {
                DangKyKhoaHoc dk =
                        dangKyKhoaHocService.findByUserAndCourse(user, kh);

                if (dk != null) {
                    courseStatusMap.put(
                            kh.getIdKhoaHoc(),
                            dk.getTrangThaiKhoaHoc()
                    );
                }
            }
        }
        mv.addObject("courseStatusMap", courseStatusMap);

        List<KhoaHoc> recentCourses = new ArrayList<>();

        if (loggedIn) {
            List<LichSuTruyCapKhoaHoc> lichSuList =
                    lichSuTruyCapKhoaHocRepository
                            .findByUser_IdDoiTuongOrderByThoiGianTruyCapGanNhatDesc(
                                    user.getIdDoiTuong()
                            );

            for (LichSuTruyCapKhoaHoc ls : lichSuList) {
                if (ls.getKhoaHoc() != null) {
                    recentCourses.add(ls.getKhoaHoc());
                }
                if (recentCourses.size() == 4) break;
            }
        }

        mv.addObject("recentCourses", recentCourses);

        return mv;
    }
}

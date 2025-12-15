package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DangKyKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TrangThaiKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDangKyKhoaHocService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IKhoaHocService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/courses")
public class CoursePageController {

    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final IDangKyKhoaHocService dangKyKhoaHocService;

    public CoursePageController(
            IKhoaHocService khoaHocService,
            IDoiTuongSuDungService doiTuongSuDungService,
            IDangKyKhoaHocService dangKyKhoaHocService) {
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.dangKyKhoaHocService = dangKyKhoaHocService;
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

        // Map: khoaHocId -> TrangThaiKhoaHoc
        Map<String, TrangThaiKhoaHoc> courseStatusMap = new HashMap<>();

//        if (loggedIn) {
//            for (KhoaHoc kh : courses) {
//                DangKyKhoaHoc dk =
//                        dangKyKhoaHocService.findByUserAndCourse(user, kh);
//
//                if (dk != null) {
//                    courseStatusMap.put(
//                            kh.getIdKhoaHoc(),
//                            dk.getTrangThai()
//                    );
//                }
//            }
//        }
        if (loggedIn) {
            for (KhoaHoc kh : courses) {

                System.out.println("CHECK COURSE ID = " + kh.getIdKhoaHoc());

                DangKyKhoaHoc dk =
                        dangKyKhoaHocService.findByUserAndCourse(user, kh);

                if (dk != null) {
                    System.out.println("FOUND DK: "
                            + kh.getTenKhoaHoc()
                            + " | STATUS = " + dk.getTrangThai());

                    courseStatusMap.put(
                            kh.getIdKhoaHoc(),
                            dk.getTrangThai()
                    );
                } else {
                    System.out.println("NO DK: " + kh.getTenKhoaHoc());
                }
            }
        }

        mv.addObject("courseStatusMap", courseStatusMap);

        return mv;
    }
}


package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LessonDetailController {

    private final BaiHocRepository baiHocRepository;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final LoTrinhHocRepository loTrinhHocRepository;
    private final TienDoHocRepository tienDoHocRepository;

    public LessonDetailController(BaiHocRepository baiHocRepository,
                                  IDoiTuongSuDungService doiTuongSuDungService,
                                  LoTrinhHocRepository loTrinhHocRepository,
                                  TienDoHocRepository tienDoHocRepository) {
        this.baiHocRepository = baiHocRepository;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.loTrinhHocRepository = loTrinhHocRepository;
        this.tienDoHocRepository = tienDoHocRepository;
    }

    @GetMapping("/lesson/{idBaiHoc}")
    public ModelAndView lessonDetail(@PathVariable String idBaiHoc, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("lesson-detail");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        mv.addObject("loggedIn", loggedIn);

        DoiTuongSuDung user = null;
        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            mv.addObject("user", user);
        }

        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null) {
            mv.setViewName("redirect:/courses");
            return mv;
        }
        mv.addObject("lesson", lesson);

        KhoaHoc course = lesson.getKhoaHoc();
        mv.addObject("course", course);

        // prev/next theo thuTu
        List<BaiHoc> lessons = baiHocRepository.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(course.getIdKhoaHoc());
        BaiHoc prev = null, next = null;

        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getIdBaiHoc().equals(idBaiHoc)) {
                if (i > 0) prev = lessons.get(i - 1);
                if (i < lessons.size() - 1) next = lessons.get(i + 1);
                break;
            }
        }

        if (loggedIn) {

            LoTrinhHoc loTrinh = loTrinhHocRepository
                    .findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            course.getIdKhoaHoc()
                    );

            if (loTrinh == null) {
                loTrinh = new LoTrinhHoc();
                loTrinh.setNguoiDung(user);
                loTrinh.setKhoaHoc(course);
                loTrinhHocRepository.save(loTrinh);
            }

            TienDoHoc tienDo = loTrinh.getTienDoHoc();

            if (tienDo == null) {
                tienDo = new TienDoHoc();
                tienDo.setLoTrinhHoc(loTrinh);
            }

            tienDo.setBaiHocHienTai(lesson);
            tienDo.setLanCuoiTruyCap(LocalDateTime.now());

            tienDoHocRepository.save(tienDo);

            loTrinh.setTienDoHoc(tienDo);
            loTrinhHocRepository.save(loTrinh);
        }

        mv.addObject("prevLesson", prev);
        mv.addObject("nextLesson", next);

        mv.addObject("activeMenu", "courses");
        return mv;
    }
}

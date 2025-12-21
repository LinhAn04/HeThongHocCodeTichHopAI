package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LessonProgressController {

    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final BaiHocRepository baiHocRepository;
    private final LoTrinhHocRepository loTrinhHocRepository;
    private final TienDoHocRepository tienDoHocRepository;

    public LessonProgressController(
            IDoiTuongSuDungService doiTuongSuDungService,
            BaiHocRepository baiHocRepository,
            LoTrinhHocRepository loTrinhHocRepository,
            TienDoHocRepository tienDoHocRepository
    ) {
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.baiHocRepository = baiHocRepository;
        this.loTrinhHocRepository = loTrinhHocRepository;
        this.tienDoHocRepository = tienDoHocRepository;
    }

    @PostMapping("/lesson/{idBaiHoc}/complete")
    public String markLessonDone(
            @PathVariable String idBaiHoc,
            HttpServletRequest request
    ) {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/login";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        BaiHoc lesson = baiHocRepository.findById(idBaiHoc).orElse(null);
        if (lesson == null) return "redirect:/courses";

        KhoaHoc course = lesson.getKhoaHoc();

        LoTrinhHoc loTrinh = loTrinhHocRepository
                .findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                        user.getIdDoiTuong(),
                        course.getIdKhoaHoc()
                );

        if (loTrinh == null || loTrinh.getTienDoHoc() == null) {
            return "redirect:/lesson/" + idBaiHoc;
        }

        TienDoHoc tienDo = loTrinh.getTienDoHoc();
        tienDo.getBaiHocDaHoanThanhIds().add(idBaiHoc);

        // tính % hoàn thành
        int totalLessons = course.getDsBaiHoc().size();
        int done = tienDo.getBaiHocDaHoanThanhIds().size();

        tienDo.setTiLeHoanThanh(
                totalLessons == 0 ? 0.0 : (done * 100.0 / totalLessons)
        );

        tienDoHocRepository.save(tienDo);

        return "redirect:/lesson/" + idBaiHoc;
    }
}
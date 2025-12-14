package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.KhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LichSuTruyCapKhoaHoc;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.LichSuTruyCapKhoaHocRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IDoiTuongSuDungService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IKhoaHocService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.ILichSuTruyCapKhoaHocService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseDetailController {

    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final ILichSuTruyCapKhoaHocService lichSuTruyCapKhoaHocService;
    private final LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository;

    public CourseDetailController(IKhoaHocService khoaHocService, IDoiTuongSuDungService doiTuongSuDungService,
                                  ILichSuTruyCapKhoaHocService lichSuTruyCapKhoaHocService,
                                  LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository) {
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.lichSuTruyCapKhoaHocService = lichSuTruyCapKhoaHocService;
        this.lichSuTruyCapKhoaHocRepository = lichSuTruyCapKhoaHocRepository;
    }

    @GetMapping("/{id}")
    public ModelAndView courseDetail(
            @PathVariable String id,
            HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");

        if (email != null) {
            DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
            KhoaHoc khoaHoc = khoaHocService.findById(id);

            lichSuTruyCapKhoaHocService.saveOrUpdate(user, khoaHoc);
        }

        ModelAndView mv = new ModelAndView("course-detail");
        mv.addObject("course", khoaHocService.findById(id));
        return mv;
    }

//    @Override
    public void saveOrUpdate(DoiTuongSuDung user, KhoaHoc khoaHoc) {

        Optional<LichSuTruyCapKhoaHoc> opt =
                lichSuTruyCapKhoaHocRepository.findByUserAndKhoaHoc(user, khoaHoc);

        LichSuTruyCapKhoaHoc ls = opt.orElse(new LichSuTruyCapKhoaHoc());
        ls.setUser(user);
        ls.setKhoaHoc(khoaHoc);
        ls.setThoiGianTruyCapGanNhat(LocalDateTime.now());

        lichSuTruyCapKhoaHocRepository.save(ls);
    }
}

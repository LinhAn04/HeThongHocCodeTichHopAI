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

}
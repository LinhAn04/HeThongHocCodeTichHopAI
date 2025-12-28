package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CourseDetailController {

    private final IKhoaHocService khoaHocService;
    private final IDoiTuongSuDungService doiTuongSuDungService;

    private final BaiHocRepository baiHocRepository;
    private final DanhGiaRepository danhGiaRepository;
    private final PhanHoiDanhGiaRepository phanHoiDanhGiaRepository;
    private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
    private final LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository;
    private final LoTrinhHocRepository loTrinhHocRepository;

    public CourseDetailController(
            IKhoaHocService khoaHocService,
            IDoiTuongSuDungService doiTuongSuDungService,
            BaiHocRepository baiHocRepository,
            DanhGiaRepository danhGiaRepository,
            PhanHoiDanhGiaRepository phanHoiDanhGiaRepository,
            DangKyKhoaHocRepository dangKyKhoaHocRepository,
            LichSuTruyCapKhoaHocRepository lichSuTruyCapKhoaHocRepository,
            LoTrinhHocRepository loTrinhHocRepository
    ) {
        this.khoaHocService = khoaHocService;
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.baiHocRepository = baiHocRepository;
        this.danhGiaRepository = danhGiaRepository;
        this.phanHoiDanhGiaRepository = phanHoiDanhGiaRepository;
        this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
        this.lichSuTruyCapKhoaHocRepository = lichSuTruyCapKhoaHocRepository;
        this.loTrinhHocRepository = loTrinhHocRepository;
    }

    @GetMapping("/course/{idKhoaHoc}")
    public ModelAndView courseDetail(
            @PathVariable String idKhoaHoc,
            @RequestParam(name = "intent", required = false, defaultValue = "try") String intent,
            HttpServletRequest request
    ) {
        ModelAndView mv = new ModelAndView("course_detail");

        String email = (String) request.getSession().getAttribute("email");
        boolean loggedIn = (email != null);
        mv.addObject("loggedIn", loggedIn);

        DoiTuongSuDung user = null;
        if (loggedIn) {
            user = doiTuongSuDungService.findByEmail(email);
            mv.addObject("user", user);
        }

        KhoaHoc khoaHoc = khoaHocService.findById(idKhoaHoc);
        if (khoaHoc == null) {
            mv.setViewName("redirect:/courses");
            return mv;
        }
        mv.addObject("course", khoaHoc);

        // list bài học theo thứ tự
        List<BaiHoc> allLessons =
                baiHocRepository.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(idKhoaHoc);

        List<BaiHoc> visibleLessons = new ArrayList<>();

        DangKyKhoaHoc dk = null;
        boolean hasRegistered = (dk != null); // đã đăng ký khóa học hay chưa

        for (BaiHoc l : allLessons) {
            if (l.getIsActive()) {
                // rule 2: bài active -> luôn hiện
                visibleLessons.add(l);
            } else if (hasRegistered) {
                // rule 3: bài inactive nhưng đã đăng ký -> vẫn hiện
                visibleLessons.add(l);
            }
        }

        mv.addObject("lessons", visibleLessons);

        // trạng thái đăng ký
        dk = null;
        TrangThaiKhoaHoc status = null;
        if (loggedIn) {
            dk = dangKyKhoaHocRepository
                    .findByNguoiHoc_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            idKhoaHoc
                    )
                    .orElse(null);

            if (dk != null) {
                status = dk.getTrangThaiKhoaHoc();
            }
        }
        mv.addObject("dangKy", dk);
        mv.addObject("courseStatus", status);

        // ghi lịch sử truy cập khóa học (chỉ khi logged in)
        if (loggedIn) {
            upsertCourseAccess(user, khoaHoc);
        }

        // comment + reply
        List<DanhGia> comments = danhGiaRepository.findByKhoaHoc_IdKhoaHocOrderByThoiGianDanhGiaDesc(idKhoaHoc);
        for (DanhGia c : comments) {
            if (c.getNguoiDung() != null) {
                DoiTuongSuDung fullUser =
                        doiTuongSuDungService.findById(
                                c.getNguoiDung().getIdDoiTuong()
                        );
                c.setNguoiDung(fullUser);
            }
        }
        mv.addObject("comments", comments);

        Map<String, List<PhanHoiDanhGia>> repliesMap = new HashMap<>();
        for (DanhGia dg : comments) {
            List<PhanHoiDanhGia> replies =
                    phanHoiDanhGiaRepository
                            .findByDanhGia_IdDanhGiaOrderByThoiGianPhanHoiAsc(dg.getIdDanhGia());
            for (PhanHoiDanhGia r : replies) {
                if (r.getNguoiDung() != null) {
                    DoiTuongSuDung fullUser =
                            doiTuongSuDungService.findById(
                                    r.getNguoiDung().getIdDoiTuong()
                            );
                    r.setNguoiDung(fullUser);
                }
                if (loggedIn && r.getNguoiDung() != null) {
                    boolean canEdit =
                            r.getNguoiDung().getIdDoiTuong().equals(user.getIdDoiTuong())
                                    && r.getThoiGianPhanHoi()
                                    .plusHours(24)
                                    .isAfter(LocalDateTime.now());

                    r.setCanEdit(canEdit);
                }
            }
            if (loggedIn && dg.getNguoiDung() != null) {
                boolean canEdit =
                        dg.getNguoiDung().getIdDoiTuong().equals(user.getIdDoiTuong())
                                && dg.getThoiGianDanhGia()
                                .plusHours(24)
                                .isAfter(LocalDateTime.now());

                dg.setCanEdit(canEdit);
            }
            repliesMap.put(dg.getIdDanhGia(), replies);
        }
        mv.addObject("repliesMap", repliesMap);

        // intent: try / start / continue
        mv.addObject("intent", intent);

        LoTrinhHoc loTrinh = null;
        TienDoHoc tienDo = null;

        if (loggedIn) {
            loTrinh = loTrinhHocRepository
                    .findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(
                            user.getIdDoiTuong(),
                            idKhoaHoc
                    );
            if (loTrinh != null) {
                tienDo = loTrinh.getTienDoHoc();
            }
        }

        if (tienDo != null && tienDo.getBaiHocHienTai() != null) {
            mv.addObject("continueLessonId",
                    tienDo.getBaiHocHienTai().getIdBaiHoc());
        }

        Set<String> unlockedLessonIds = new HashSet<>();

        List<BaiHoc> lessons =
                baiHocRepository.findByKhoaHoc_IdKhoaHocOrderByThuTuAsc(idKhoaHoc);

        if (!loggedIn || dk == null) {
            int count = 0;
            for (BaiHoc l : visibleLessons) {
                unlockedLessonIds.add(l.getIdBaiHoc());
                count++;
                if (count == 4) break;
            }
        }
        else {
            // đã đăng nhập
            if (tienDo != null && tienDo.getBaiHocHienTai() != null) {
                int currentOrder = tienDo.getBaiHocHienTai().getThuTu();

                for (BaiHoc l : lessons) {
                    // mở các bài đã học + bài kế tiếp
                    if (l.getThuTu() <= currentOrder) {
                        unlockedLessonIds.add(l.getIdBaiHoc());
                    }
                }
            } else {
                // logged in nhưng chưa có tiến độ → cho mở bài đầu tiên
                if (!lessons.isEmpty()) {
                    unlockedLessonIds.add(lessons.get(0).getIdBaiHoc());
                }
            }
        }

        mv.addObject("unlockedLessonIds", unlockedLessonIds);
        mv.addObject("openTab", "lessons");
        mv.addObject("tienDo", tienDo);

        return mv;
    }

    private void upsertCourseAccess(DoiTuongSuDung user, KhoaHoc course) {
        LichSuTruyCapKhoaHoc ls = lichSuTruyCapKhoaHocRepository
                .findByUser_IdDoiTuongAndKhoaHoc_IdKhoaHoc(user.getIdDoiTuong(), course.getIdKhoaHoc());

        if (ls == null) {
            ls = new LichSuTruyCapKhoaHoc();
            ls.setUser(user);
            ls.setKhoaHoc(course);
        }
        ls.setThoiGianTruyCapGanNhat(LocalDateTime.now());
        lichSuTruyCapKhoaHocRepository.save(ls);
    }
}
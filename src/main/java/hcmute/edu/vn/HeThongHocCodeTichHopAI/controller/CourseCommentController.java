package hcmute.edu.vn.HeThongHocCodeTichHopAI.controller;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CourseCommentController {

    private final IDoiTuongSuDungService doiTuongSuDungService;
    private final IKhoaHocService khoaHocService;
    private final DanhGiaRepository danhGiaRepository;
    private final PhanHoiDanhGiaRepository phanHoiDanhGiaRepository;

    public CourseCommentController(
            IDoiTuongSuDungService doiTuongSuDungService,
            IKhoaHocService khoaHocService,
            DanhGiaRepository danhGiaRepository,
            PhanHoiDanhGiaRepository phanHoiDanhGiaRepository
    ) {
        this.doiTuongSuDungService = doiTuongSuDungService;
        this.khoaHocService = khoaHocService;
        this.danhGiaRepository = danhGiaRepository;
        this.phanHoiDanhGiaRepository = phanHoiDanhGiaRepository;
    }

    @PostMapping("/course/{idKhoaHoc}/comment")
    public String postComment(@PathVariable String idKhoaHoc,
                              @RequestParam("noiDung") String noiDung,
                              HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/course/" + idKhoaHoc;

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        KhoaHoc course = khoaHocService.findById(idKhoaHoc);

        DanhGia dg = new DanhGia();
        dg.setNoiDungDanhGia(noiDung);
        dg.setThoiGianDanhGia(LocalDateTime.now());
        dg.setNguoiDung(user);
        dg.setKhoaHoc(course);
        dg.setSoLanChinhSua(0);

        danhGiaRepository.save(dg);

        return "redirect:/course/" + idKhoaHoc + "#comments.css";
    }

    @PostMapping("/comment/{idDanhGia}/reply")
    public String postReply(@PathVariable String idDanhGia,
                            @RequestParam("noiDung") String noiDung,
                            HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/courses";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        DanhGia dg = danhGiaRepository.findById(idDanhGia).orElse(null);
        if (dg == null) return "redirect:/courses";

        PhanHoiDanhGia ph = new PhanHoiDanhGia();
        ph.setNoiDungPhanHoi(noiDung);
        ph.setThoiGianPhanHoi(LocalDateTime.now());
        ph.setNguoiDung(user);
        ph.setDanhGia(dg);
        ph.setSoLanChinhSua(0);

        phanHoiDanhGiaRepository.save(ph);

        return "redirect:/course/" + dg.getKhoaHoc().getIdKhoaHoc() + "#comments.css";
    }

    @PostMapping("/comment/{idDanhGia}/edit")
    public String editComment(@PathVariable String idDanhGia,
                              @RequestParam("noiDung") String noiDungMoi,
                              HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/courses";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        DanhGia dg = danhGiaRepository.findById(idDanhGia).orElse(null);
        if (dg == null) return "redirect:/courses";

        // ❌ Không phải chủ
        if (!dg.getNguoiDung().getIdDoiTuong().equals(user.getIdDoiTuong())) {
            return "redirect:/course/" + dg.getKhoaHoc().getIdKhoaHoc();
        }

        // ❌ Quá 24h
        if (dg.getThoiGianDanhGia().plusHours(24).isBefore(LocalDateTime.now())) {
            return "redirect:/course/" + dg.getKhoaHoc().getIdKhoaHoc();
        }

        // ⭐ LƯU LỊCH SỬ
        LichSuChinhSua ls = new LichSuChinhSua();
        ls.setNoiDungCu(dg.getNoiDungDanhGia());
        ls.setThoiGianChinhSua(LocalDateTime.now());
        ls.setNguoiChinhSuaId(user.getIdDoiTuong());

        dg.getLichSuChinhSua().add(ls);

        // update nội dung mới
        dg.setNoiDungDanhGia(noiDungMoi);
        dg.setSoLanChinhSua(dg.getSoLanChinhSua() + 1);

        danhGiaRepository.save(dg);

        return "redirect:/course/" + dg.getKhoaHoc().getIdKhoaHoc() + "#comments.css";
    }

    @PostMapping("/reply/{idPhanHoi}/edit")
    public String editReply(@PathVariable String idPhanHoi,
                            @RequestParam("noiDung") String noiDungMoi,
                            HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        if (email == null) return "redirect:/courses";

        DoiTuongSuDung user = doiTuongSuDungService.findByEmail(email);
        PhanHoiDanhGia ph = phanHoiDanhGiaRepository.findById(idPhanHoi).orElse(null);
        if (ph == null) return "redirect:/courses";

        if (!ph.getNguoiDung().getIdDoiTuong().equals(user.getIdDoiTuong())) {
            return "redirect:/course/" + ph.getDanhGia().getKhoaHoc().getIdKhoaHoc();
        }

        if (ph.getThoiGianPhanHoi().plusHours(24).isBefore(LocalDateTime.now())) {
            return "redirect:/course/" + ph.getDanhGia().getKhoaHoc().getIdKhoaHoc();
        }

        LichSuChinhSua ls = new LichSuChinhSua();
        ls.setNoiDungCu(ph.getNoiDungPhanHoi());
        ls.setThoiGianChinhSua(LocalDateTime.now());
        ls.setNguoiChinhSuaId(user.getIdDoiTuong());

        ph.getLichSuChinhSua().add(ls);

        ph.setNoiDungPhanHoi(noiDungMoi);
        ph.setSoLanChinhSua(ph.getSoLanChinhSua() + 1);

        phanHoiDanhGiaRepository.save(ph);

        return "redirect:/course/" + ph.getDanhGia().getKhoaHoc().getIdKhoaHoc() + "#comments.css";
    }
}
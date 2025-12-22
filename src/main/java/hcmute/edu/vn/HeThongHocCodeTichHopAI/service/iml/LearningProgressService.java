package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.*;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LearningProgressService {
    private final LoTrinhHocRepository loTrinhHocRepository;
    private final TienDoHocRepository tienDoHocRepository;
    private final BaiHocRepository baiHocRepository;

    public LearningProgressService(
            LoTrinhHocRepository loTrinhHocRepository,
            TienDoHocRepository tienDoHocRepository,
            BaiHocRepository baiHocRepository
    ) {
        this.loTrinhHocRepository = loTrinhHocRepository;
        this.tienDoHocRepository = tienDoHocRepository;
        this.baiHocRepository = baiHocRepository;
    }

    public TienDoHoc ensureProgress(DoiTuongSuDung user, KhoaHoc course) {
        LoTrinhHoc loTrinh = loTrinhHocRepository
                .findByNguoiDung_IdDoiTuongAndKhoaHoc_IdKhoaHoc(user.getIdDoiTuong(), course.getIdKhoaHoc());

        if (loTrinh != null && loTrinh.getTienDoHoc() != null) {
            return loTrinh.getTienDoHoc();
        }

        // create mới
        BaiHoc first = baiHocRepository.findFirstByKhoaHoc_IdKhoaHocOrderByThuTuAsc(course.getIdKhoaHoc());
        TienDoHoc td = new TienDoHoc();
        td.setBaiHocHienTai(first);
        td.setCapNhatLuc(LocalDateTime.now());
        tienDoHocRepository.save(td);

        if (loTrinh == null) {
            loTrinh = new LoTrinhHoc();
            loTrinh.setNguoiDung(user);
            loTrinh.setKhoaHoc(course);
        }
        loTrinh.setTienDoHoc(td);
        loTrinhHocRepository.save(loTrinh);

        return td;
    }

    public void markCompletedAndMoveNext(TienDoHoc td, BaiHoc current, BaiHoc next) {
        List<String> done = td.getBaiHocDaHoanThanhIds();
        if (!done.contains(current.getIdBaiHoc())) {
            done.add(current.getIdBaiHoc());
        }
        td.setBaiHocHienTai(next != null ? next : current);
        td.setCapNhatLuc(LocalDateTime.now());
        tienDoHocRepository.save(td);
    }
}

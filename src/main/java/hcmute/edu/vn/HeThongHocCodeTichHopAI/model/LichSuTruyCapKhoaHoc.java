package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "lich_su_truy_cap_khoa_hoc")
public class LichSuTruyCapKhoaHoc {

    @Id
    private String idLichSuTruyCapKhoaHoc;

    @DBRef
    private DoiTuongSuDung user;

    @DBRef
    private KhoaHoc khoaHoc;

    private LocalDateTime thoiGianTruyCapGanNhat;

    public LichSuTruyCapKhoaHoc() {}

    public String getIdLichSuTruyCapKhoaHoc() {
        return idLichSuTruyCapKhoaHoc;
    }
    public void setIdLichSuTruyCapKhoaHoc(String idLichSuTruyCapKhoaHoc) {
        this.idLichSuTruyCapKhoaHoc = idLichSuTruyCapKhoaHoc;
    }

    public DoiTuongSuDung getUser() {
        return user;
    }
    public void setUser(DoiTuongSuDung user) {
        this.user = user;
    }

    public KhoaHoc getKhoaHoc() {
        return khoaHoc;
    }
    public void setKhoaHoc(KhoaHoc khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public LocalDateTime getThoiGianTruyCapGanNhat() {
        return thoiGianTruyCapGanNhat;
    }
    public void setThoiGianTruyCapGanNhat(LocalDateTime thoiGianTruyCapGanNhat) {
        this.thoiGianTruyCapGanNhat = thoiGianTruyCapGanNhat;
    }

}


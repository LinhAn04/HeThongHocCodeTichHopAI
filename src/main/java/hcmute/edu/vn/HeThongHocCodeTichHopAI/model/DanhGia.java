package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "danh_gia")
public class DanhGia {
    @Id
    private String idDanhGia;

    private String noiDungDanhGia;

    private Integer diemDanhGia;

    private LocalDateTime thoiGianDanhGia;

    @DBRef // tham chiếu sang collection "khoa_hoc"
    private KhoaHoc khoaHoc;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung nguoiDung;

    @DBRef
    private Set<PhanHoiDanhGia> dsPhanHoi = new HashSet<>();

    public DanhGia() {}

    public String getIdDanhGia() { return idDanhGia; }
    public void setIdDanhGia(String idDanhGia) { this.idDanhGia = idDanhGia; }

    public String getNoiDungDanhGia() { return noiDungDanhGia; }
    public void setNoiDungDanhGia(String noiDungDanhGia) { this.noiDungDanhGia = noiDungDanhGia; }

    public Integer getDiemDanhGia() { return diemDanhGia; }
    public void setDiemDanhGia(Integer diemDanhGia) { this.diemDanhGia = diemDanhGia; }

    public LocalDateTime getThoiGianDanhGia() { return thoiGianDanhGia; }
    public void setThoiGianDanhGia(LocalDateTime thoiGianDanhGia) { this.thoiGianDanhGia = thoiGianDanhGia; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public Set<PhanHoiDanhGia> getDsPhanHoi() { return dsPhanHoi; }
    public void setDsPhanHoi(Set<PhanHoiDanhGia> dsPhanHoi) { this.dsPhanHoi = dsPhanHoi; }
}

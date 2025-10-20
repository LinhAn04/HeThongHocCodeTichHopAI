package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "hoa_don")
public class HoaDon {
    @Id
    private String idHoaDon;

    private LocalDateTime thoiGianThanhToan;

    private Double tongTien;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung nguoiDung;

    @DBRef // tham chiếu sang collection "khoa_hoc"
    private KhoaHoc khoaHoc;

    public HoaDon() {}

    public String getIdHoaDon() { return idHoaDon; }
    public void setIdHoaDon(String idHoaDon) { this.idHoaDon = idHoaDon; }

    public LocalDateTime getThoiGianThanhToan() { return thoiGianThanhToan; }
    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) { this.thoiGianThanhToan = thoiGianThanhToan; }

    public Double getTongTien() { return tongTien; }
    public void setTongTien(Double tongTien) { this.tongTien = tongTien; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }
}

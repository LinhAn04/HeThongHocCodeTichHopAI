package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "khoa_hoc")
public class KhoaHoc {
    @Id
    private String idKhoaHoc;

    private String anhBiaKhoaHoc;

    private String tenKhoaHoc;

    private String moTa;

    private double giaBan;

    private TrangThaiKhoaHoc trangThaiKhoaHoc;

    private LocalDateTime thoiGianDangKy;

    private LocalDateTime thoiGianKetThuc;

    @DBRef // tham chiếu sang collection "bai_hoc"
    private Set<BaiHoc> dsBaiHoc = new HashSet<>();

    @DBRef // tham chiếu sang collection "danh_gia"
    private Set<DanhGia> dsDanhGia = new HashSet<>();

    public KhoaHoc() {}

    public String getIdKhoaHoc() {
        return idKhoaHoc;
    }
    public void setIdKhoaHoc(String idKhoaHoc) {
        this.idKhoaHoc = idKhoaHoc;
    }

    public String getAnhBiaKhoaHoc() { return anhBiaKhoaHoc; }
    public void setAnhBiaKhoaHoc(String anhBiaKhoaHoc) { this.anhBiaKhoaHoc = anhBiaKhoaHoc; }

    public String getTenKhoaHoc() {
        return tenKhoaHoc;
    }
    public void setTenKhoaHoc(String tenKhoaHoc) {
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public String getMoTa() {
        return moTa;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public TrangThaiKhoaHoc getTrangThaiKhoaHoc() {
        return trangThaiKhoaHoc;
    }
    public void setTrangThaiKhoaHoc(TrangThaiKhoaHoc trangThaiKhoaHoc) {
        this.trangThaiKhoaHoc = trangThaiKhoaHoc;
    }

    public LocalDateTime getThoiGianDangKy() {
        return thoiGianDangKy;
    }
    public void setThoiGianDangKy(LocalDateTime thoiGianDangKy) {
        this.thoiGianDangKy = thoiGianDangKy;
    }

    public LocalDateTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }
    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public Set<BaiHoc> getDsBaiHoc() {
        return dsBaiHoc;
    }
    public void setDsBaiHoc(Set<BaiHoc> dsBaiHoc) {
        this.dsBaiHoc = dsBaiHoc;
    }

    public Set<DanhGia> getDsDanhGia() {
        return dsDanhGia;
    }
    public void setDsDanhGia(Set<DanhGia> dsDanhGia) {
        this.dsDanhGia = dsDanhGia;
    }
}
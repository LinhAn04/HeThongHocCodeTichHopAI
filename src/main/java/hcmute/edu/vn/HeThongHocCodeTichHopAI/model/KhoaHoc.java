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

    private String moTaKhoaHoc;

    private double giaBan;

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

    public String getMoTaKhoaHoc() {
        return  moTaKhoaHoc;
    }
    public void setMoTaKhoaHoc(String moTaKhoaHoc) {
        this.moTaKhoaHoc = moTaKhoaHoc;
    }

    public double getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
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
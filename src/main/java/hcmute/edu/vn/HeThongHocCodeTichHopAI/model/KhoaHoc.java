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

    private String tacGia;

    private LocalDateTime ngayTao;

    private String tenKhoaHoc;

    private String moTaKhoaHoc;

    private long giaBan;

    private Boolean isActive = true;

    private Boolean hasEnrollment = false;

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

    public String getAnhBiaKhoaHoc() {
        return anhBiaKhoaHoc;
    }

    public void setAnhBiaKhoaHoc(String anhBiaKhoaHoc) {
        this.anhBiaKhoaHoc = anhBiaKhoaHoc;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

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

    public long getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(long giaBan) {
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getHasEnrollment() {
        return hasEnrollment;
    }

    public void setHasEnrollment(Boolean hasEnrollment) {
        this.hasEnrollment = hasEnrollment;
    }
}
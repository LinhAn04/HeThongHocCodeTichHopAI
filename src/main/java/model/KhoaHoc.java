package model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "khoa_hoc")
public class KhoaHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khoa_hoc")
    private int idKhoaHoc;

    @Column(name = "ten_khoa_hoc", nullable = false)
    private String tenKhoaHoc;

    @Column(name = "mo_ta_khoa_hoc")
    private String moTaKhoaHoc;

    @Column(name = "gia_ban")
    private double giaBan;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai_khoa_hoc")
    private TrangThaiKhoaHoc trangThaiKhoaHoc;

    @Column(name = "thoi_gian_dang_ky")
    private LocalDateTime thoiGianDangKy;

    @Column(name = "thoi_gian_ket_thuc")
    private LocalDateTime thoiGianKetThuc;

    @OneToMany(mappedBy = "khoaHoc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BaiHoc> dsBaiHoc = new HashSet<>();

    @OneToMany(mappedBy = "khoaHoc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DanhGia> dsDanhGia = new HashSet<>();

    public KhoaHoc() {}

    public KhoaHoc(String tenKhoaHoc, String moTaKhoaHoc, double giaBan, TrangThaiKhoaHoc trangThaiKhoaHoc, LocalDateTime thoiGianDangKy, LocalDateTime thoiGianKetThuc) {
        this.tenKhoaHoc = tenKhoaHoc;
        this.moTaKhoaHoc = moTaKhoaHoc;
        this.giaBan = giaBan;
        this.trangThaiKhoaHoc = trangThaiKhoaHoc;
        this.thoiGianDangKy = thoiGianDangKy;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public int getIdKhoaHoc() {
        return idKhoaHoc;
    }

    public void setIdKhoaHoc(int idKhoaHoc) {
        this.idKhoaHoc = idKhoaHoc;
    }

    public String getTenKhoaHoc() {
        return tenKhoaHoc;
    }

    public void setTenKhoaHoc(String tenKhoaHoc) {
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public String getMoTaKhoaHoc() {
        return moTaKhoaHoc;
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

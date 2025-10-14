package model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doi_tuong_su_dung")
public class DoiTuongSuDung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doi_tuong")
    private int idDoiTuong;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "ngay_sinh")
    private LocalDateTime ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    private GioiTinh gioiTinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_doi_tuong", nullable = false)
    private LoaiDoiTuongSuDung loaiDoiTuongSuDung = LoaiDoiTuongSuDung.KHACHHANG;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tk_doi_tuong", nullable = false, unique = true)
    private TKDoiTuongSuDung taiKhoan;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DanhGia> dsDanhGia = new HashSet<>();

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PhanHoiDanhGia> dsPhanHoi = new HashSet<>();

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<HoaDon> dsHoaDon = new HashSet<>();

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<LoTrinhHoc> dsLoTrinhHoc = new HashSet<>();

    @OneToMany(mappedBy = "tacGia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BaiBlog> dsBaiBlog = new HashSet<>();

    @OneToMany(mappedBy = "nguoiTao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ThongBao> dsThongBao = new HashSet<>();

    public DoiTuongSuDung() {}

    public int getIdDoiTuong() { return idDoiTuong; }
    public void setIdDoiTuong(int idDoiTuong) { this.idDoiTuong = idDoiTuong; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDateTime ngaySinh) { this.ngaySinh = ngaySinh; }

    public GioiTinh getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(GioiTinh gioiTinh) { this.gioiTinh = gioiTinh; }

    public LoaiDoiTuongSuDung getLoaiDoiTuongSuDung() { return loaiDoiTuongSuDung; }
    public void setLoaiDoiTuongSuDung(LoaiDoiTuongSuDung loaiDoiTuongSuDung) { this.loaiDoiTuongSuDung = loaiDoiTuongSuDung; }

    public TKDoiTuongSuDung getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(TKDoiTuongSuDung taiKhoan) { this.taiKhoan = taiKhoan; }

    public Set<DanhGia> getDsDanhGia() { return dsDanhGia; }
    public void setDsDanhGia(Set<DanhGia> dsDanhGia) { this.dsDanhGia = dsDanhGia; }

    public Set<PhanHoiDanhGia> getDsPhanHoi() { return dsPhanHoi; }
    public void setDsPhanHoi(Set<PhanHoiDanhGia> dsPhanHoi) { this.dsPhanHoi = dsPhanHoi; }

    public Set<HoaDon> getDsHoaDon() { return dsHoaDon; }
    public void setDsHoaDon(Set<HoaDon> dsHoaDon) { this.dsHoaDon = dsHoaDon; }

    public Set<LoTrinhHoc> getDsLoTrinhHoc() { return dsLoTrinhHoc; }
    public void setDsLoTrinhHoc(Set<LoTrinhHoc> dsLoTrinhHoc) { this.dsLoTrinhHoc = dsLoTrinhHoc; }

    public Set<BaiBlog> getDsBaiBlog() { return dsBaiBlog; }
    public void setDsBaiBlog(Set<BaiBlog> dsBaiBlog) { this.dsBaiBlog = dsBaiBlog; }

    public Set<ThongBao> getDsThongBao() { return dsThongBao; }
    public void setDsThongBao(Set<ThongBao> dsThongBao) { this.dsThongBao = dsThongBao; }
}

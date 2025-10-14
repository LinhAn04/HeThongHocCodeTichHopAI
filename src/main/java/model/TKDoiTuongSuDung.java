package model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tk_doi_tuong_su_dung")
public class TKDoiTuongSuDung implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tk_doi_tuong")
    private int idTKDoiTuong;

    @Column(name = "ten_dang_nhap", nullable = false, unique = true, length = 100)
    private String tenDangNhap;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "trang_thai_kich_hoat", nullable = false)
    private boolean trangThaiKichHoat = true;

    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private DoiTuongSuDung doiTuongSuDung;

    public TKDoiTuongSuDung() {}

    public TKDoiTuongSuDung(String tenDangNhap, String matKhau, boolean trangThaiKichHoat) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThaiKichHoat = trangThaiKichHoat;
    }

    public int getIdTKDoiTuong() { return idTKDoiTuong; }
    public void setIdTKDoiTuong(int idTKDoiTuong) { this.idTKDoiTuong = idTKDoiTuong; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public boolean isTrangThaiKichHoat() { return trangThaiKichHoat; }
    public void setTrangThaiKichHoat(boolean trangThaiKichHoat) { this.trangThaiKichHoat = trangThaiKichHoat; }

    public DoiTuongSuDung getDoiTuongSuDung() { return doiTuongSuDung; }
    public void setDoiTuongSuDung(DoiTuongSuDung doiTuongSuDung) { this.doiTuongSuDung = doiTuongSuDung; }
}

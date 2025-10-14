package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "phan_hoi_danh_gia")
public class PhanHoiDanhGia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phan_hoi")
    private int idPhanHoiDanhGia;

    @Column(name = "noi_dung_phan_hoi")
    private String noiDungPhanHoi;

    @Column(name = "thoi_gian_phan_hoi")
    private LocalDateTime thoiGianPhanHoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_danh_gia", nullable = false)
    private DanhGia danhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private DoiTuongSuDung nguoiDung;

    public PhanHoiDanhGia() {}

    public int getIdPhanHoiDanhGia() { return idPhanHoiDanhGia; }
    public void setIdPhanHoiDanhGia(int idPhanHoiDanhGia) { this.idPhanHoiDanhGia = idPhanHoiDanhGia; }

    public String getNoiDungPhanHoi() { return noiDungPhanHoi; }
    public void setNoiDungPhanHoi(String noiDungPhanHoi) { this.noiDungPhanHoi = noiDungPhanHoi; }

    public LocalDateTime getThoiGianPhanHoi() { return thoiGianPhanHoi; }
    public void setThoiGianPhanHoi(LocalDateTime thoiGianPhanHoi) { this.thoiGianPhanHoi = thoiGianPhanHoi; }

    public DanhGia getDanhGia() { return danhGia; }
    public void setDanhGia(DanhGia danhGia) { this.danhGia = danhGia; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }
}

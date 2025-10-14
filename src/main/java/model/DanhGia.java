package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "danh_gia")
public class DanhGia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_danh_gia")
    private int idDanhGia;

    @Column(name = "noi_dung_danh_gia")
    private String noiDungDanhGia;

    @Column(name = "diem_danh_gia")
    private Integer diemDanhGia;

    @Column(name = "thoi_gian_danh_gia")
    private LocalDateTime thoiGianDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khoa_hoc", nullable = false)
    private KhoaHoc khoaHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private DoiTuongSuDung nguoiDung;

    @OneToMany(mappedBy = "danhGia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PhanHoiDanhGia> dsPhanHoi = new HashSet<>();

    public DanhGia() {}

    public int getIdDanhGia() { return idDanhGia; }
    public void setIdDanhGia(int idDanhGia) { this.idDanhGia = idDanhGia; }

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

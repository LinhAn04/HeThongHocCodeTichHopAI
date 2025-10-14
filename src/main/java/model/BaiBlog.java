package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "bai_blog")
public class BaiBlog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bai_blog")
    private int idBaiBlog;

    @Column(name = "tieu_de_bai_blog", nullable = false)
    private String tieuDeBaiBlog;

    @Lob
    @Column(name = "noi_dung_bai_blog")
    private String noiDungBaiBlog;

    @Column(name = "thoi_gian_dang_bai")
    private LocalDateTime thoiGianDangBai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tac_gia", nullable = false)
    private DoiTuongSuDung tacGia;

    public BaiBlog() {}

    public int getIdBaiBlog() { return idBaiBlog; }
    public void setIdBaiBlog(int idBaiBlog) { this.idBaiBlog = idBaiBlog; }

    public String getTieuDeBaiBlog() { return tieuDeBaiBlog; }
    public void setTieuDeBaiBlog(String tieuDeBaiBlog) { this.tieuDeBaiBlog = tieuDeBaiBlog; }

    public String getNoiDungBaiBlog() { return noiDungBaiBlog; }
    public void setNoiDungBaiBlog(String noiDungBaiBlog) { this.noiDungBaiBlog = noiDungBaiBlog; }

    public LocalDateTime getThoiGianDangBai() { return thoiGianDangBai; }
    public void setThoiGianDangBai(LocalDateTime thoiGianDangBai) { this.thoiGianDangBai = thoiGianDangBai; }

    public DoiTuongSuDung getTacGia() { return tacGia; }
    public void setTacGia(DoiTuongSuDung tacGia) { this.tacGia = tacGia; }
}

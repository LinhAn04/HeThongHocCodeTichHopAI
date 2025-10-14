package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "thong_bao")
public class ThongBao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_thong_bao")
    private int idThongBao;

    @Column(name = "tieu_de_thong_bao", nullable = false)
    private String tieuDeThongBao;

    @Lob
    @Column(name = "noi_dung_thong_bao")
    private String noiDungThongBao;

    @Column(name = "thoi_gian_dang_tb")
    private LocalDateTime thoiGianDangTB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_tao", nullable = false)
    private DoiTuongSuDung nguoiTao;

    public ThongBao() {}

    public int getIdThongBao() { return idThongBao; }
    public void setIdThongBao(int idThongBao) { this.idThongBao = idThongBao; }

    public String getTieuDeThongBao() { return tieuDeThongBao; }
    public void setTieuDeThongBao(String tieuDeThongBao) { this.tieuDeThongBao = tieuDeThongBao; }

    public String getNoiDungThongBao() { return noiDungThongBao; }
    public void setNoiDungThongBao(String noiDungThongBao) { this.noiDungThongBao = noiDungThongBao; }

    public LocalDateTime getThoiGianDangTB() { return thoiGianDangTB; }
    public void setThoiGianDangTB(LocalDateTime thoiGianDangTB) { this.thoiGianDangTB = thoiGianDangTB; }

    public DoiTuongSuDung getNguoiTao() { return nguoiTao; }
    public void setNguoiTao(DoiTuongSuDung nguoiTao) { this.nguoiTao = nguoiTao; }
}

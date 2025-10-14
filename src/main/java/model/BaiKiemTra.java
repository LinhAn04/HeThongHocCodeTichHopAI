package model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "bai_kiem_tra")
public class BaiKiemTra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bai_kiem_tra")
    private int idBaiKiemTra;

    @Lob
    @Column(name = "noi_dung_bai_kiem_tra")
    private String noiDungBaiKiemTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bai_hoc", nullable = false)
    private BaiHoc baiHoc;

    public BaiKiemTra() {}

    public int getIdBaiKiemTra() { return idBaiKiemTra; }
    public void setIdBaiKiemTra(int idBaiKiemTra) { this.idBaiKiemTra = idBaiKiemTra; }

    public String getNoiDungBaiKiemTra() { return noiDungBaiKiemTra; }
    public void setNoiDungBaiKiemTra(String noiDungBaiKiemTra) { this.noiDungBaiKiemTra = noiDungBaiKiemTra; }

    public BaiHoc getBaiHoc() { return baiHoc; }
    public void setBaiHoc(BaiHoc baiHoc) { this.baiHoc = baiHoc; }
}

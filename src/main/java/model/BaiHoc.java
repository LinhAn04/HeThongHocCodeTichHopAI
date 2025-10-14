package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bai_hoc")
public class BaiHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bai_hoc")
    private int idBaiHoc;

    @Column(name = "mo_ta_bai_hoc")
    private String moTaBaiHoc;

    @Lob
    @Column(name = "noi_dung_bai_hoc")
    private String noiDungBaiHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khoa_hoc", nullable = false)
    private KhoaHoc khoaHoc;

    @OneToMany(mappedBy = "baiHoc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BaiKiemTra> dsBaiKiemTra = new HashSet<>();

    public BaiHoc() {}

    public int getIdBaiHoc() { return idBaiHoc; }
    public void setIdBaiHoc(int idBaiHoc) { this.idBaiHoc = idBaiHoc; }

    public String getMoTaBaiHoc() { return moTaBaiHoc; }
    public void setMoTaBaiHoc(String moTaBaiHoc) { this.moTaBaiHoc = moTaBaiHoc; }

    public String getNoiDungBaiHoc() { return noiDungBaiHoc; }
    public void setNoiDungBaiHoc(String noiDungBaiHoc) { this.noiDungBaiHoc = noiDungBaiHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public Set<BaiKiemTra> getDsBaiKiemTra() { return dsBaiKiemTra; }
    public void setDsBaiKiemTra(Set<BaiKiemTra> dsBaiKiemTra) { this.dsBaiKiemTra = dsBaiKiemTra; }
}

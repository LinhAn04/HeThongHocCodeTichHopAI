package model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "lo_trinh_hoc")
public class LoTrinhHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lo_trinh_hoc")
    private int idLoTrinhHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khoa_hoc", nullable = false)
    private KhoaHoc khoaHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private DoiTuongSuDung nguoiDung;

    @OneToOne(mappedBy = "loTrinhHoc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TienDoHoc tienDoHoc;

    public LoTrinhHoc() {}

    public int getIdLoTrinhHoc() { return idLoTrinhHoc; }
    public void setIdLoTrinhHoc(int idLoTrinhHoc) { this.idLoTrinhHoc = idLoTrinhHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public TienDoHoc getTienDoHoc() { return tienDoHoc; }
    public void setTienDoHoc(TienDoHoc tienDoHoc) {
        this.tienDoHoc = tienDoHoc;
        if (tienDoHoc != null) {
            tienDoHoc.setLoTrinhHoc(this);
        }
    }
}

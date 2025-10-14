package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tien_do_hoc")
public class TienDoHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tien_do_hoc")
    private int idTienDoHoc;

    @Column(name = "ti_le_hoan_thanh")
    private Double tiLeHoanThanh;

    @Column(name = "lan_cuoi_truy_cap")
    private LocalDateTime lanCuoiTruyCap;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lo_trinh_hoc", nullable = false, unique = true)
    private LoTrinhHoc loTrinhHoc;

    public TienDoHoc() {}

    public int getIdTienDoHoc() { return idTienDoHoc; }
    public void setIdTienDoHoc(int idTienDoHoc) { this.idTienDoHoc = idTienDoHoc; }

    public Double getTiLeHoanThanh() { return tiLeHoanThanh; }
    public void setTiLeHoanThanh(Double tiLeHoanThanh) { this.tiLeHoanThanh = tiLeHoanThanh; }

    public LocalDateTime getLanCuoiTruyCap() { return lanCuoiTruyCap; }
    public void setLanCuoiTruyCap(LocalDateTime lanCuoiTruyCap) { this.lanCuoiTruyCap = lanCuoiTruyCap; }

    public LoTrinhHoc getLoTrinhHoc() { return loTrinhHoc; }
    public void setLoTrinhHoc(LoTrinhHoc loTrinhHoc) { this.loTrinhHoc = loTrinhHoc; }
}

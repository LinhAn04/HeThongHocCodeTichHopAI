package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "tien_do_hoc")
public class TienDoHoc {
    @Id
    private String idTienDoHoc;

    private Double tiLeHoanThanh = 0.0;

    private LocalDateTime lanCuoiTruyCap;

    @DBRef
    private BaiHoc baiHocHienTai;

    private Set<String> baiHocDaHoanThanhIds = new HashSet<>();

    @DBRef
    private LoTrinhHoc loTrinhHoc;

    public TienDoHoc() {}

    public String getIdTienDoHoc() { return idTienDoHoc; }
    public void setIdTienDoHoc(String idTienDoHoc) { this.idTienDoHoc = idTienDoHoc; }

    public Double getTiLeHoanThanh() { return tiLeHoanThanh; }
    public void setTiLeHoanThanh(Double tiLeHoanThanh) { this.tiLeHoanThanh = tiLeHoanThanh; }

    public LocalDateTime getLanCuoiTruyCap() { return lanCuoiTruyCap; }
    public void setLanCuoiTruyCap(LocalDateTime lanCuoiTruyCap) { this.lanCuoiTruyCap = lanCuoiTruyCap; }

    public LoTrinhHoc getLoTrinhHoc() { return loTrinhHoc; }
    public void setLoTrinhHoc(LoTrinhHoc loTrinhHoc) { this.loTrinhHoc = loTrinhHoc; }

    public BaiHoc getBaiHocHienTai() { return baiHocHienTai; }
    public void setBaiHocHienTai(BaiHoc baiHocHienTai) { this.baiHocHienTai = baiHocHienTai; }

    public Set<String> getBaiHocDaHoanThanhIds() { return baiHocDaHoanThanhIds; }
    public void setBaiHocDaHoanThanhIds(Set<String> baiHocDaHoanThanhIds) {}
}

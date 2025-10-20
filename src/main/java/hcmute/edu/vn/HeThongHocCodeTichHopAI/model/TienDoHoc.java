package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tien_do_hoc")
public class TienDoHoc {
    @Id
    private String idTienDoHoc;

    private Double tiLeHoanThanh;

    private LocalDateTime lanCuoiTruyCap;

    @DBRef // tham chiếu sang collection "lo_trinh_hoc"
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
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "bai_hoc")
public class BaiHoc {
    @Id
    private String idBaiHoc;

    private String tieuDeBaiHoc;

    private Integer thuTu;

    /*LY_THUYET(1),
    TRAC_NGHIEM(2),
    VIDEO(3),
    CODE(4);*/
    private Integer loai;

    private String youtubeVideoId;

    private String codeDeBai;

    private String codeGoiY;

    private String codeOutputMau;

    private String starterCode;

    private String moTaBaiHoc;

    private String noiDungBaiHoc;

    @DBRef // tham chiếu sang collection "khoa_hoc"
    private KhoaHoc khoaHoc;

    private Boolean isActive;

    public BaiHoc() {}

    public String getIdBaiHoc() { return idBaiHoc; }
    public void setIdBaiHoc(String idBaiHoc) { this.idBaiHoc = idBaiHoc; }

    public String getTieuDeBaiHoc() { return tieuDeBaiHoc; }
    public void setTieuDeBaiHoc(String tieuDeBaiHoc) { this.tieuDeBaiHoc = tieuDeBaiHoc; }

    public Integer getThuTu() { return thuTu; }
    public void setThuTu(Integer thuTu) { this.thuTu = thuTu; }

    public Integer getLoai() { return loai; }
    public void setLoai(Integer loai) { this.loai = loai; }

    public String getYoutubeVideoId() { return youtubeVideoId; }
    public void setYoutubeVideoId(String videoUrl) { this.youtubeVideoId = youtubeVideoId; }

    public String getCodeDeBai() { return codeDeBai; }
    public void setCodeDeBai(String codeDeBai) { this.codeDeBai = codeDeBai; }

    public String getCodeGoiY() { return codeGoiY; }
    public void setCodeGoiY(String codeGoiY) { this.codeGoiY = codeGoiY; }

    public String getCodeOutputMau() { return codeOutputMau; }
    public void setCodeOutputMau(String codeOutputMau) { this.codeOutputMau = codeOutputMau; }

    public String getStarterCode() { return starterCode; }
    public void setStarterCode(String starterCode) { this.starterCode = starterCode; }

    public String getMoTaBaiHoc() { return moTaBaiHoc; }
    public void setMoTaBaiHoc(String moTaBaiHoc) { this.moTaBaiHoc = moTaBaiHoc; }

    public String getNoiDungBaiHoc() { return noiDungBaiHoc; }
    public void setNoiDungBaiHoc(String noiDungBaiHoc) { this.noiDungBaiHoc = noiDungBaiHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}

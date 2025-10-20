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

    private String moTaBaiHoc;

    private String noiDungBaiHoc;

    @DBRef // tham chiếu sang collection "khoa_hoc"
    private KhoaHoc khoaHoc;

    @DBRef // tham chiếu sang collection "bai_kiem_tra"
    private Set<BaiKiemTra> dsBaiKiemTra = new HashSet<>();

    public BaiHoc() {}

    public String getIdBaiHoc() { return idBaiHoc; }
    public void setIdBaiHoc(String idBaiHoc) { this.idBaiHoc = idBaiHoc; }

    public String getMoTaBaiHoc() { return moTaBaiHoc; }
    public void setMoTaBaiHoc(String moTaBaiHoc) { this.moTaBaiHoc = moTaBaiHoc; }

    public String getNoiDungBaiHoc() { return noiDungBaiHoc; }
    public void setNoiDungBaiHoc(String noiDungBaiHoc) { this.noiDungBaiHoc = noiDungBaiHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public Set<BaiKiemTra> getDsBaiKiemTra() { return dsBaiKiemTra; }
    public void setDsBaiKiemTra(Set<BaiKiemTra> dsBaiKiemTra) { this.dsBaiKiemTra = dsBaiKiemTra; }
}

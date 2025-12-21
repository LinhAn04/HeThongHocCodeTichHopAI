package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "bai_kiem_tra")
public class BaiKiemTra {
    @Id
    private String idBaiKiemTra;

    private String tieuDe;

    private List<CauHoiTracNghiem> cauHoi = new ArrayList<>();

    private BaiHoc baiHoc;

    public BaiKiemTra() {}

    public String getIdBaiKiemTra() { return idBaiKiemTra; }
    public void setIdBaiKiemTra(String idBaiKiemTra) { this.idBaiKiemTra = idBaiKiemTra; }

    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }

    public List<CauHoiTracNghiem> getCauHoi() {return cauHoi;}
    public void setCauHoi(List<CauHoiTracNghiem> cauHoi) { this.cauHoi = cauHoi;}

    public BaiHoc getBaiHoc() { return baiHoc; }
    public void setBaiHoc(BaiHoc baiHoc) { this.baiHoc = baiHoc; }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bai_kiem_tra")
public class BaiKiemTra {
    @Id
    private String idBaiKiemTra;

    private String noiDungBaiKiemTra;

    private BaiHoc baiHoc;

    public BaiKiemTra() {}

    public String getIdBaiKiemTra() { return idBaiKiemTra; }
    public void setIdBaiKiemTra(String idBaiKiemTra) { this.idBaiKiemTra = idBaiKiemTra; }

    public String getNoiDungBaiKiemTra() { return noiDungBaiKiemTra; }
    public void setNoiDungBaiKiemTra(String noiDungBaiKiemTra) { this.noiDungBaiKiemTra = noiDungBaiKiemTra; }

    public BaiHoc getBaiHoc() { return baiHoc; }
    public void setBaiHoc(BaiHoc baiHoc) { this.baiHoc = baiHoc; }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "tien_do_hoc")
public class TienDoHoc {
    @Id
    private String idTienDoHoc;

    @DBRef
    private BaiHoc baiHocHienTai;

    private double tiLeHoanThanh;

    // chứa các idBaiHoc đã hoàn thành
    private List<String> baiHocDaHoanThanhIds = new ArrayList<>();

    private LocalDateTime capNhatLuc = LocalDateTime.now();

    public String getIdTienDoHoc() { return idTienDoHoc; }
    public void setIdTienDoHoc(String idTienDoHoc) { this.idTienDoHoc = idTienDoHoc; }

    public BaiHoc getBaiHocHienTai() { return baiHocHienTai; }
    public void setBaiHocHienTai(BaiHoc baiHocHienTai) { this.baiHocHienTai = baiHocHienTai; }

    public double getTiLeHoanThanh() { return tiLeHoanThanh; }
    public void setTiLeHoanThanh(double tiLeHoanThanh) { this.tiLeHoanThanh = tiLeHoanThanh; }

    public List<String> getBaiHocDaHoanThanhIds() { return baiHocDaHoanThanhIds; }
    public void setBaiHocDaHoanThanhIds(List<String> ids) { this.baiHocDaHoanThanhIds = ids; }

    public LocalDateTime getCapNhatLuc() { return capNhatLuc; }
    public void setCapNhatLuc(LocalDateTime capNhatLuc) { this.capNhatLuc = capNhatLuc; }
}

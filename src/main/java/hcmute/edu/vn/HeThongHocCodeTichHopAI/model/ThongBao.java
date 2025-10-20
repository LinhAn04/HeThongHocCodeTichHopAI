package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "lo_trinh_hoc")
public class ThongBao {
    @Id
    private String idThongBao;

    private String tieuDeThongBao;

    private String noiDungThongBao;

    private LocalDateTime thoiGianDangTB;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung nguoiTao;

    public ThongBao() {}

    public String getIdThongBao() { return idThongBao; }
    public void setIdThongBao(String idThongBao) { this.idThongBao = idThongBao; }

    public String getTieuDeThongBao() { return tieuDeThongBao; }
    public void setTieuDeThongBao(String tieuDeThongBao) { this.tieuDeThongBao = tieuDeThongBao; }

    public String getNoiDungThongBao() { return noiDungThongBao; }
    public void setNoiDungThongBao(String noiDungThongBao) { this.noiDungThongBao = noiDungThongBao; }

    public LocalDateTime getThoiGianDangTB() { return thoiGianDangTB; }
    public void setThoiGianDangTB(LocalDateTime thoiGianDangTB) { this.thoiGianDangTB = thoiGianDangTB; }

    public DoiTuongSuDung getNguoiTao() { return nguoiTao; }
    public void setNguoiTao(DoiTuongSuDung nguoiTao) { this.nguoiTao = nguoiTao; }
}

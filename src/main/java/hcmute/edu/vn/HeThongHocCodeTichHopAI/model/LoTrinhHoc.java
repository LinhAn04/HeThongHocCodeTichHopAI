package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lo_trinh_hoc")
public class LoTrinhHoc {
    @Id
    private String idLoTrinhHoc;

    @DBRef // tham chiếu sang collection "khoa_hoc"
    private KhoaHoc khoaHoc;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung nguoiDung;

    @DBRef // tham chiếu sang collection "tien_do_hoc"
    private TienDoHoc tienDoHoc;

    public LoTrinhHoc() {}

    public String getIdLoTrinhHoc() { return idLoTrinhHoc; }
    public void setIdLoTrinhHoc(String idLoTrinhHoc) { this.idLoTrinhHoc = idLoTrinhHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public TienDoHoc getTienDoHoc() { return tienDoHoc; }
    public void setTienDoHoc(TienDoHoc tienDoHoc) { this.tienDoHoc = tienDoHoc; }
}

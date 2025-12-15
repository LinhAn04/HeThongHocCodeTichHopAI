package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bai_blog")
public class BaiBlog {
    @Id
    private String idBaiBlog;

    private String anhBiaBaiBlog;

    private String tieuDeBaiBlog;

    private String noiDungBaiBlog;

    private LocalDateTime thoiGianDangBai;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung tacGia;

    public BaiBlog() {}

    public String getIdBaiBlog() { return idBaiBlog; }
    public void setIdBaiBlog(String idBaiBlog) { this.idBaiBlog = idBaiBlog; }

    public String getAnhBiaBaiBlog() { return anhBiaBaiBlog; }
    public void setAnhBiaBaiBlog(String anhBiaBaiBlog) { this.anhBiaBaiBlog = anhBiaBaiBlog; }

    public String getTieuDeBaiBlog() { return tieuDeBaiBlog; }
    public void setTieuDeBaiBlog(String tieuDeBaiBlog) { this.tieuDeBaiBlog = tieuDeBaiBlog; }

    public String getNoiDungBaiBlog() { return noiDungBaiBlog; }
    public void setNoiDungBaiBlog(String noiDungBaiBlog) { this.noiDungBaiBlog = noiDungBaiBlog; }

    public LocalDateTime getThoiGianDangBai() { return thoiGianDangBai; }
    public void setThoiGianDangBai(LocalDateTime thoiGianDangBai) { this.thoiGianDangBai = thoiGianDangBai; }

    public DoiTuongSuDung getTacGia() { return tacGia; }
    public void setTacGia(DoiTuongSuDung tacGia) { this.tacGia = tacGia; }
}

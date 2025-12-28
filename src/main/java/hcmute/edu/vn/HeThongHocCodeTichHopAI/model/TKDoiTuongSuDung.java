package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tk_doi_tuong_su_dung")
public class TKDoiTuongSuDung {
    @Id
    private String idTKDoiTuong;

    private String tenDangNhap;

    private String matKhau;

    private boolean trangThaiKichHoat = true;

    private boolean isActive = true;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung doiTuongSuDung;

    public TKDoiTuongSuDung() {}

    public TKDoiTuongSuDung(String tenDangNhap, String matKhau, boolean trangThaiKichHoat) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThaiKichHoat = trangThaiKichHoat;
    }

    public String getIdTKDoiTuong() { return idTKDoiTuong; }
    public void setIdTKDoiTuong(String idTKDoiTuong) { this.idTKDoiTuong = idTKDoiTuong; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public boolean isTrangThaiKichHoat() { return trangThaiKichHoat; }
    public void setTrangThaiKichHoat(boolean trangThaiKichHoat) { this.trangThaiKichHoat = trangThaiKichHoat; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public DoiTuongSuDung getDoiTuongSuDung() { return doiTuongSuDung; }
    public void setDoiTuongSuDung(DoiTuongSuDung doiTuongSuDung) { this.doiTuongSuDung = doiTuongSuDung; }
}
package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "dang_ky_khoa_hoc")
public class DangKyKhoaHoc {
    @Id
    private String idDangKyKhoaHoc;

    @DBRef
    private DoiTuongSuDung nguoiHoc;

    @DBRef
    private KhoaHoc khoaHoc;

    private TrangThaiKhoaHoc trangThaiKhoaHoc;

    private LocalDateTime thoiGianDangKy;
    private LocalDateTime thoiGianKetThuc;

    public DangKyKhoaHoc() {}

    public String getIdDangKyKhoaHoc() { return idDangKyKhoaHoc; }
    public void setIdDangKyKhoaHoc(String idDangKyKhoaHoc) { this.idDangKyKhoaHoc = idDangKyKhoaHoc; }

    public DoiTuongSuDung getNguoiHoc() { return nguoiHoc; }
    public void setDoiTuongSuDung(DoiTuongSuDung nguoiHoc) { this.nguoiHoc = nguoiHoc; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public TrangThaiKhoaHoc getTrangThai() { return trangThaiKhoaHoc; }
    public void setTrangThaiKhoaHoc(TrangThaiKhoaHoc trangThaiKhoaHoc) { this.trangThaiKhoaHoc = trangThaiKhoaHoc; }

    public LocalDateTime getThoiGianDangKy() { return thoiGianDangKy; }
    public void setThoiGianDangKy(LocalDateTime thoiGianDangKy){ this.thoiGianDangKy = thoiGianDangKy; }

    public LocalDateTime getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc){ this.thoiGianKetThuc = thoiGianKetThuc; }
}

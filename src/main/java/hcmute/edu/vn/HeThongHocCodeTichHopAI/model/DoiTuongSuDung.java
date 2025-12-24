package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "doi_tuong_su_dung")
public class DoiTuongSuDung {
    @Id
    private String idDoiTuong;

    private String hoTen;

    private String email;

    private LoaiDoiTuongSuDung loaiDoiTuongSuDung = LoaiDoiTuongSuDung.STUDENT;

    private LocalDate ngaySinh;

    private String soDienThoai;

    private String diaChi;

    private GioiTinh gioiTinh;

    private String avatar;

    @DBRef // tham chiếu sang collection "tk_doi_tuong_su_dung"
    private TKDoiTuongSuDung taiKhoan;

    @DBRef // tham chiếu sang collection "danh_gia"
    private Set<DanhGia> dsDanhGia = new HashSet<>();

    @DBRef // tham chiếu sang collection "phan_hoi_danh_gia"
    private Set<PhanHoiDanhGia> dsPhanHoi = new HashSet<>();

    @DBRef // tham chiếu sang collection "hoa_don"
    private Set<HoaDon> dsHoaDon = new HashSet<>();

    @DBRef // tham chiếu sang collection "lo_trinh_hoc"
    private Set<LoTrinhHoc> dsLoTrinhHoc = new HashSet<>();

    @DBRef // tham chiếu sang collection "bai_blog"
    private Set<BaiBlog> dsBaiBlog = new HashSet<>();

//    @DBRef // tham chiếu sang collection "thong_bao"
//    private Set<ThongBao> dsThongBao = new HashSet<>();

    public DoiTuongSuDung() {}

    public String getIdDoiTuong() { return idDoiTuong; }
    public void setIdDoiTuong(String idDoiTuong) { this.idDoiTuong = idDoiTuong; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LoaiDoiTuongSuDung getLoaiDoiTuongSuDung() { return loaiDoiTuongSuDung; }
    public void setLoaiDoiTuongSuDung(LoaiDoiTuongSuDung loaiDoiTuongSuDung) { this.loaiDoiTuongSuDung = loaiDoiTuongSuDung; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public GioiTinh getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(GioiTinh gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getAvatar () { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public TKDoiTuongSuDung getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(TKDoiTuongSuDung taiKhoan) { this.taiKhoan = taiKhoan; }

    public Set<DanhGia> getDsDanhGia() { return dsDanhGia; }
    public void setDsDanhGia(Set<DanhGia> dsDanhGia) { this.dsDanhGia = dsDanhGia; }

    public Set<PhanHoiDanhGia> getDsPhanHoi() { return dsPhanHoi; }
    public void setDsPhanHoi(Set<PhanHoiDanhGia> dsPhanHoi) { this.dsPhanHoi = dsPhanHoi; }

    public Set<HoaDon> getDsHoaDon() { return dsHoaDon; }
    public void setDsHoaDon(Set<HoaDon> dsHoaDon) { this.dsHoaDon = dsHoaDon; }

    public Set<LoTrinhHoc> getDsLoTrinhHoc() { return dsLoTrinhHoc; }
    public void setDsLoTrinhHoc(Set<LoTrinhHoc> dsLoTrinhHoc) { this.dsLoTrinhHoc = dsLoTrinhHoc; }

    public Set<BaiBlog> getDsBaiBlog() { return dsBaiBlog; }
    public void setDsBaiBlog(Set<BaiBlog> dsBaiBlog) { this.dsBaiBlog = dsBaiBlog; }

//    public Set<ThongBao> getDsThongBao() { return dsThongBao; }
//    public void setDsThongBao(Set<ThongBao> dsThongBao) { this.dsThongBao = dsThongBao; }
}

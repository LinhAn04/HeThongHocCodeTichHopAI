package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "phan_hoi_danh_gia")
public class PhanHoiDanhGia {
    @Id
    private String idPhanHoiDanhGia;

    private String noiDungPhanHoi;

    private LocalDateTime thoiGianPhanHoi;

    @DBRef // tham chiếu sang collection "danh_gia"
    private DanhGia danhGia;

    @DBRef // tham chiếu sang collection "doi_tuong_su_dung"
    private DoiTuongSuDung nguoiDung;

    private LocalDateTime lanChinhSuaCuoi;

    private Integer soLanChinhSua = 0;

    @Transient
    private boolean canEdit;

    private List<LichSuChinhSua> lichSuChinhSua = new ArrayList<>();

    public PhanHoiDanhGia() {}

    public String getIdPhanHoiDanhGia() { return idPhanHoiDanhGia; }
    public void setIdPhanHoiDanhGia(String idPhanHoiDanhGia) { this.idPhanHoiDanhGia = idPhanHoiDanhGia; }

    public String getNoiDungPhanHoi() { return noiDungPhanHoi; }
    public void setNoiDungPhanHoi(String noiDungPhanHoi) { this.noiDungPhanHoi = noiDungPhanHoi; }

    public LocalDateTime getThoiGianPhanHoi() { return thoiGianPhanHoi; }
    public void setThoiGianPhanHoi(LocalDateTime thoiGianPhanHoi) { this.thoiGianPhanHoi = thoiGianPhanHoi; }

    public DanhGia getDanhGia() { return danhGia; }
    public void setDanhGia(DanhGia danhGia) { this.danhGia = danhGia; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public LocalDateTime getLanChinhSuaCuoi() { return lanChinhSuaCuoi; }
    public void setLanChinhSuaCuoi(LocalDateTime lanChinhSuaCuoi) { this.lanChinhSuaCuoi = lanChinhSuaCuoi; }

    public Integer getSoLanChinhSua() { return soLanChinhSua; }
    public void setSoLanChinhSua(Integer soLanChinhSua) { this.soLanChinhSua = soLanChinhSua; }

    public boolean getCanEdit() { return canEdit; }
    public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }

    public List<LichSuChinhSua> getLichSuChinhSua() { return lichSuChinhSua; }
    public void setLichSuChinhSua(List<Integer> lichSuChinhSua) {}
}

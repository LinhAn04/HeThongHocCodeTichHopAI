package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "danh_gia")
public class DanhGia {
    @Id
    private String idDanhGia;

    private String noiDungDanhGia;

    private LocalDateTime thoiGianDanhGia;

    private Integer soLanChinhSua = 0;

    @DBRef
    private DoiTuongSuDung nguoiDung;

    @DBRef
    private KhoaHoc khoaHoc;

    @Transient
    private boolean canEdit;

    private List<LichSuChinhSua> lichSuChinhSua = new ArrayList<>();

    @DBRef
    private Set<PhanHoiDanhGia> dsPhanHoi = new HashSet<>();

    public DanhGia() {}

    public String getIdDanhGia() { return idDanhGia; }
    public void setIdDanhGia(String idDanhGia) { this.idDanhGia = idDanhGia; }

    public String getNoiDungDanhGia() { return noiDungDanhGia; }
    public void setNoiDungDanhGia(String noiDungDanhGia) { this.noiDungDanhGia = noiDungDanhGia; }

    public LocalDateTime getThoiGianDanhGia() { return thoiGianDanhGia; }
    public void setThoiGianDanhGia(LocalDateTime thoiGianDanhGia) { this.thoiGianDanhGia = thoiGianDanhGia; }

    public KhoaHoc getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(KhoaHoc khoaHoc) { this.khoaHoc = khoaHoc; }

    public DoiTuongSuDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(DoiTuongSuDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public Integer getSoLanChinhSua() { return soLanChinhSua; }
    public void setSoLanChinhSua(Integer soLanChinhSua) { this.soLanChinhSua = soLanChinhSua; }

    public boolean getCanEdit() { return canEdit; }
    public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }

    public List<LichSuChinhSua> getLichSuChinhSua() { return lichSuChinhSua; }
    public void setLichSuChinhSua(List<Integer> lichSuChinhSua) {}

    public Set<PhanHoiDanhGia> getDsPhanHoi() { return dsPhanHoi; }
    public void setDsPhanHoi(Set<PhanHoiDanhGia> dsPhanHoi) { this.dsPhanHoi = dsPhanHoi; }
}

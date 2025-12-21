package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import java.time.LocalDateTime;

public class LichSuChinhSua {
    private String noiDungCu;

    private LocalDateTime thoiGianChinhSua;

    private String nguoiChinhSuaId;

    public LichSuChinhSua() {}

    public String getNoiDungCu() { return noiDungCu; }
    public void setNoiDungCu(String noiDungCu) { this.noiDungCu = noiDungCu; }

    public LocalDateTime getThoiGianChinhSua() { return thoiGianChinhSua; }
    public void setThoiGianChinhSua(LocalDateTime thoiGianChinhSua) { this.thoiGianChinhSua = thoiGianChinhSua; }

    public String getNguoiChinhSuaId() { return nguoiChinhSuaId; }
    public void setNguoiChinhSuaId(String nguoiChinhSuaId) { this.nguoiChinhSuaId = nguoiChinhSuaId; }
}

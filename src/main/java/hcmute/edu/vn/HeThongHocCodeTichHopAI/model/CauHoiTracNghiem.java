package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import java.util.ArrayList;
import java.util.List;

public class CauHoiTracNghiem {
    private String cauHoi;
    private List<String> luaChon = new ArrayList<>();
    private Integer dapAnDungIndex;
    private String giaiThich;

    public CauHoiTracNghiem() {}

    public String getCauHoi() { return cauHoi;}
    public void setCauHoi(String cauHoi) {this.cauHoi = cauHoi;}

    public List<String> getLuaChon() { return luaChon;}
    public void setLuaChon(List<String> luaChon) {this.luaChon = luaChon;}

    public Integer getDapAnDungIndex() { return dapAnDungIndex;}
    public void setDapAnDungIndex(Integer dapAnDungIndex) {this.dapAnDungIndex = dapAnDungIndex;}

    public String getGiaiThich() { return giaiThich;}
    public void setGiaiThich(String GiaiThich) {this.giaiThich = giaiThich;}
}

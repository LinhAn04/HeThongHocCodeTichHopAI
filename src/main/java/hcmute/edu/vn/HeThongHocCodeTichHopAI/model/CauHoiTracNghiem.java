package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import java.util.ArrayList;
import java.util.List;

public class CauHoiTracNghiem {
    private String cauHoi;

    private List<String> luaChon = new ArrayList<>();

    private Integer dapAnDungIndex;

    private String giaiThichDung;                 // vì sao đáp án đúng đúng

    private List<String> giaiThichSai = new ArrayList<>();

    public CauHoiTracNghiem() {}

    public String getCauHoi() { return cauHoi;}
    public void setCauHoi(String cauHoi) {this.cauHoi = cauHoi;}

    public List<String> getLuaChon() { return luaChon;}
    public void setLuaChon(List<String> luaChon) {this.luaChon = luaChon;}

    public Integer getDapAnDungIndex() { return dapAnDungIndex;}
    public void setDapAnDungIndex(Integer dapAnDungIndex) {this.dapAnDungIndex = dapAnDungIndex;}

    public String getGiaiThichDung() { return giaiThichDung;}
    public void setGiaiThichDung(String GiaiThich) {this.giaiThichDung = giaiThichDung;}

    public List<String> getGiaiThichSai() { return giaiThichSai;}
    public void setGiaiThichSai(List<String> giaiThichSai) {this.giaiThichSai = giaiThichSai;}
}

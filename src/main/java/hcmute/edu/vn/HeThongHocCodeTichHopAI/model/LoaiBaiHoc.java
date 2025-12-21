package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

public enum LoaiBaiHoc {
    LY_THUYET(1),
    TRAC_NGHIEM(2),
    VIDEO(3),
    CODE(4);

    private final int value;

    LoaiBaiHoc(int value) { this.value = value; }
    public int getValue() { return value; }

    public static LoaiBaiHoc fromValue(int v) {
        for (LoaiBaiHoc t : values()) {
            if (t.value == v) return t;
        }
        return LY_THUYET;
    }
}

package hcmute.edu.vn.HeThongHocCodeTichHopAI.model;

import lombok.Data;

@Data
public class MomoCreateResponse {
    private int resultCode;
    private String message;
    private String payUrl;
    private String orderId;

    public MomoCreateResponse() {}

    public int getResultCode() {
        return resultCode;
    }
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPayUrl() {
        return payUrl;
    }
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

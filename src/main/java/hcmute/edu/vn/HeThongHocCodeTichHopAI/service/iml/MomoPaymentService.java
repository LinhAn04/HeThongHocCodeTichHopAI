package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.iml;

import com.fasterxml.jackson.databind.ObjectMapper;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.MomoCreateRequest;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.MomoCreateResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpHeaders;
import java.util.UUID;

@Service
public class MomoPaymentService {

    @Value("${momo.endpoint}")
    private String endpoint;

    @Value("${momo.access-key}")
    private String accessKey;

    @Value("${momo.partner-code}")
    private String partnerCode;

    @Value("${momo.secret-key}")
    private String secretKey;

    @Value("${momo.return-url}")
    private String returnUrl;

    @Value("${momo.notify-url}")
    private String notifyUrl;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public String createPayment(String courseId, long amount) throws Exception {

        String requestId = UUID.randomUUID().toString();
        String orderId = "COURSE_" + courseId + "_" + System.currentTimeMillis();

        String rawSignature =
                "accessKey=" + accessKey +
                        "&amount=" + amount +
                        "&extraData=" +
                        "&ipnUrl=" + notifyUrl +
                        "&orderId=" + orderId +
                        "&orderInfo=Thanh toan khoa hoc" +
                        "&partnerCode=" + partnerCode +
                        "&redirectUrl=" + returnUrl +
                        "&requestId=" + requestId +
                        "&requestType=captureWallet";

        String signature = hmacSHA256(rawSignature, secretKey);

        MomoCreateRequest request = new MomoCreateRequest();
        request.setPartnerCode(partnerCode);
        request.setAccessKey(accessKey);
        request.setRequestId(requestId);
        request.setAmount(String.valueOf(amount));
        request.setOrderId(orderId);
        request.setOrderInfo("Thanh toan khoa hoc");
        request.setRedirectUrl(returnUrl);
        request.setIpnUrl(notifyUrl);
        request.setRequestType("captureWallet");
        request.setExtraData("");
        request.setSignature(signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>(mapper.writeValueAsString(request), headers);

        ResponseEntity<MomoCreateResponse> response =
                restTemplate.postForEntity(
                        endpoint + "/create",
                        entity,
                        MomoCreateResponse.class
                );

        if (response.getBody() == null || response.getBody().getResultCode() != 0) {
            throw new RuntimeException("MoMo create payment failed");
        }

        return response.getBody().getPayUrl();
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Hex.encodeHexString(rawHmac);
    }
}

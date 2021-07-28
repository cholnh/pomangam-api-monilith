package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template;

import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.BizmApi;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.formatter.PhoneNumberFormatter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class AuthTemplate {
    private final static String msg1 = "[포만감] 인증번호 : ";
    private final static String msg2 = "\n정확히 입력해주세요.";
    private final static String tmplId = "pmg_auth_1";

    public static ResponseEntity<?> send(String phoneNumber, Map<String, String> data) {
        return BizmApi.sendMsg(PhoneNumberFormatter.format(phoneNumber), (msg1 + data.get("auth_code") + msg2), tmplId);
    }

    public static void send(List<String> phoneNumbers, Map<String, String> data) {
        for(String phoneNumber : phoneNumbers) {
            send(phoneNumber, data);
        }
    }
}

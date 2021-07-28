package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template;

import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.BizmApi;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class OrderApproveTemplate {
    private final static String msg1 = "주문이 정상적으로 접수되었습니다.";

    private final static String msg2 = "\n■ 식별번호 : ";
    private final static String msg3 = "\n■ 받는장소 : ";
    private final static String msg4 = "\n■ 받는시간 : ";
    private final static String msg5 = "\n■ 주문내역 : ";
    private final static String tmplId = "pmg_order_success_1";

    public static ResponseEntity<?> send(String phoneNumber, Map<String, String> data) {
//        System.out.println("bizm : [phoneNumber : " + phoneNumber + " // tmplId : " + tmplId + "]");
//        System.out.println((
//                msg1 +
//                msg2 + data.get("order_bn") +
//                msg3 + data.get("order_addr") +
//                msg4 + data.get("order_date") +
//                msg5 + data.get("order_items"))
//        );
//        return null;
        return BizmApi.send(phoneNumber, (
                msg1 +
                msg2 + data.get("order_bn") +
                msg3 + data.get("order_addr") +
                msg4 + data.get("order_date") +
                msg5 + data.get("order_items")
            ), tmplId, data.get("order_bn"));
    }

    public static void send(List<String> phoneNumbers, Map<String, String> data) {
        for(String phoneNumber : phoneNumbers) {
            send(phoneNumber, data);
        }
    }
}

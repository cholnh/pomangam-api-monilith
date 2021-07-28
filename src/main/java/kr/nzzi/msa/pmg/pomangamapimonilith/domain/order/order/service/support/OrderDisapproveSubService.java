package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.client.FcmClientTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.client.FcmClientToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service.impl.FcmServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.exception.OrderException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.OrdererType;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.OrderDisapproveTemplate;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.formatter.PhoneNumberFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderDisapproveSubService {

    FcmServiceImpl fcmService;
    FcmClientTokenJpaRepository clientTokenRepo;

    public void verifyIsValidDisapproveOrder(OrderType orderType) {
        boolean isValid = false;
        switch (orderType) {
            case PAYMENT_READY:
            case PAYMENT_READY_FAIL_POINT:
            case PAYMENT_READY_FAIL_COUPON:
            case PAYMENT_READY_FAIL_PROMOTION:
            case PAYMENT_SUCCESS:
            case ORDER_READY:
            case ORDER_QUICK_READY:
            case ORDER_SUCCESS:
            case DELIVERY_READY:
            case DELIVERY_DELAY:
            case DELIVERY_PICKUP:
            case DELIVERY_SUCCESS:
                isValid = true;
                break;
        }
        if(!isValid) {
            throw  new OrderException("invalid order disapprove.");
        }
    }

    public void sendFcm(Order order, String reason) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "order_disapprove");

            List<FcmTokenDto> to = new ArrayList<>();
            FcmClientToken clientToken = clientTokenRepo.findByIdxAndIsActiveIsTrue(order.getOrderer().getIdxFcmToken());
            if(clientToken != null) {
                to.add(FcmTokenDto.builder()
                        .token(clientToken.getToken())
                        .build());

                // fcm build
                FcmRequestDto dto = FcmRequestDto.builder()
                        .title("(주문 실패 안내) 가게 사정으로 인해 주문이 취소되었습니다.")
                        .body(reason)
                        .data(data)
                        .to(to)
                        .build();
                fcmService.send(dto);
            }
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }

    public void sendKakaoAT(Order order, String reason) {
        try {
            if(order.getOrderer().getOrdererType() == OrdererType.GUEST && order.getOrderer().getPhoneNumber() == null) return;

            Map<String, String> data = new HashMap<>();

            data.put("order_idx", "no." + order.getIdx() + " (" + order.getBoxNumber() + "번)");
            data.put("order_date", CommonSubService.getOrderDateWithAdditionalTime(order));
            data.put("order_items", CommonSubService.orderItemLongText(order));
            data.put("reason", reason);
            if(order.getOrderer().getUser() != null) {
                OrderDisapproveTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getUser().getPhoneNumber()), data);
            } else {
                OrderDisapproveTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getPhoneNumber()), data);
            }
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }
}

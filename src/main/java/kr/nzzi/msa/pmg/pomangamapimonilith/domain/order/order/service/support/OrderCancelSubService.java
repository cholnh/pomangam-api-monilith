package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.client.FcmClientTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.owner.FcmOwnerTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.owner.FcmOwnerToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service.impl.FcmServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.exception.OrderException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.dao.jpa.OwnerJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.model.Owner;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.OrderCancelTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderCancelSubService {

    FcmServiceImpl fcmService;
    FcmClientTokenJpaRepository clientTokenRepo;
    FcmOwnerTokenJpaRepository ownerTokenRepo;
    OwnerJpaRepository ownerRepo;

    public void verifyIsValidCancelOrder(OrderType orderType) {
        boolean isValid = false;
        switch (orderType) {
            case PAYMENT_SUCCESS:
            case ORDER_READY:
            case ORDER_QUICK_READY:
                isValid = true;
                break;
        }
        if(!isValid) {
            throw  new OrderException("invalid order cancel.");
        }
    }

    public void verifyIsValidFailOrder(OrderType orderType) {
        boolean isValid = false;
        switch (orderType) {
            case PAYMENT_READY:
                isValid = true;
                break;
        }
        if(!isValid) {
            throw  new OrderException("invalid order fail.");
        }
    }


    public void sendFcm(Order order) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "order_cancel");

            // 업체별 전송할 token 정리
            List<FcmTokenDto> to = new ArrayList<>();
            for(Long idxStore : CommonSubService.getIdxStores(order)) {
                List<FcmOwnerToken> ownerTokens = ownerTokenRepo.findByStore(idxStore);
                for(FcmOwnerToken ownerToken : ownerTokens) {
                    if(ownerToken != null) {
                        to.add(FcmTokenDto.builder()
                                .token(ownerToken.getToken())
                                .build());
                    }
                }
            }

            // fcm build
            FcmRequestDto dto = FcmRequestDto.builder()
                    .title("(주문취소알림) " + CommonSubService.getOrderTime(order) + " " + order.getBoxNumber() + "번 주문이 취소되었습니다.")
                    //.body("[no." + order.getIdx() + "] " + CommonSubService.orderItemShortText(order))
                    .body("")
                    .data(data)
                    .to(to)
                    .build();
            fcmService.send(dto);
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }

    public void sendKakaoAT(Order order) {
        try {
            for(Long idxStore : CommonSubService.getIdxStores(order)) {
                List<Owner> owners = ownerRepo.findByIdxStoreAndIsActiveIsTrue(idxStore);
                for(Owner owner : owners) {
                    Map<String, String> data = new HashMap<>();
                    data.put("order_date", CommonSubService.getOrderDateWithAdditionalTime(order));
                    data.put("order_bn", CommonSubService.getOrderTime(order) + " " + order.getBoxNumber());
                    data.put("order_idx", "no." + order.getIdx() + " (" + order.getBoxNumber() + "번)");
                    data.put("order_addr", order.getDeliveryDetailSite().getFullName());
                    data.put("order_pn", CommonSubService.getOrdererPhoneNumber(order));
                    data.put("order_items", CommonSubService.orderItemLongText(order, idxStore));

                    OrderCancelTemplate.send(owner.getPhoneNumber(), data);
                }
            }


        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }
}

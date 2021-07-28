package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.model.CommonMap;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.service.impl.CommonMapServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.client.FcmClientTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.owner.FcmOwnerTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.client.FcmClientToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service.impl.FcmServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.exception.OrderException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.OrdererType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.dao.jpa.OwnerJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.OrdeRefundTemplate;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.BootpayApi;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.request.Cancel;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.formatter.PhoneNumberFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderRefundSubService {

    FcmServiceImpl fcmService;
    FcmClientTokenJpaRepository clientTokenRepo;
    FcmOwnerTokenJpaRepository ownerTokenRepo;
    OwnerJpaRepository ownerRepo;
    CommonMapServiceImpl commonMapService;
    private static BootpayApi bootpayApi = new BootpayApi();

    public boolean refundPG(String receipt_id, String name, String reason, Double price) {
        bootpayApi.getAccessToken();
        Cancel cancel = Cancel.builder()
                .receipt_id(receipt_id)
                .name(name)
                .reason(reason)
                .price(price)
                .build();
        System.out.println("PG] cancel: " + cancel);
        return bootpayApi.cancel(cancel);
    }

    public void verifyIsValidRefundOrder(OrderType orderType) {
        boolean isValid = true;
        switch (orderType) {
            case PAYMENT_REFUND:
            case PAYMENT_CANCEL:
            case PAYMENT_FAIL:
            case PAYMENT_READY_FAIL_PROMOTION:
            case PAYMENT_READY_FAIL_COUPON:
            case PAYMENT_READY_FAIL_POINT:
            case PAYMENT_READY:
                isValid = false;
                break;
        }
        if(!isValid) {
            throw  new OrderException("invalid order refund.");
        }
    }

    public void sendFcm(Order order) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "order_refund");

            List<FcmTokenDto> to = new ArrayList<>();
            FcmClientToken clientToken = clientTokenRepo.findByIdxAndIsActiveIsTrue(order.getOrderer().getIdxFcmToken());
            if(clientToken != null) {
                to.add(FcmTokenDto.builder()
                        .token(clientToken.getToken())
                        .build());

                // fcm build
                FcmRequestDto dto = FcmRequestDto.builder()
                        .title("(" + order.getBoxNumber() + "번) 환불 요청이 성공적으로 접수되었습니다.")
                        .data(data)
                        .to(to)
                        .build();
                fcmService.send(dto);
            }
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }

    public void sendKakaoAT(Order order) {
        try {
            Map<String, String> data = new HashMap<>();

            data.put("order_idx", order.getBoxNumber() + "번");
            data.put("order_date", order.getDeliveryDetailSite().getFullName());
            data.put("order_items", CommonSubService.getOrderDateWithAdditionalTime(order));
            data.put("order_refund_price", order.paymentCost() + "원");

            // 관리자 전송
            List<CommonMap> adminPhoneNumbers = commonMapService.findAllByKey("string_phonenumber_vbank_admin");
            for(CommonMap adminPhoneNumber : adminPhoneNumbers) {
                OrdeRefundTemplate.send(adminPhoneNumber.getValue(), data);
            }

            if(order.getOrderer().getOrdererType() == OrdererType.GUEST && order.getOrderer().getPhoneNumber() == null) return;
            if(order.getOrderer().getUser() != null){
                OrdeRefundTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getUser().getPhoneNumber()), data);
            } else {
                OrdeRefundTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getPhoneNumber()), data);
            }

        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.client.FcmClientTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.client.FcmClientToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service.impl.FcmServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.OrdererType;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.OrderApproveTemplate;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.formatter.PhoneNumberFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderApproveSubService {

    FcmServiceImpl fcmService;
    FcmClientTokenJpaRepository clientTokenRepo;

    public void sendFcm(Order order) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "order_approve");

            List<FcmTokenDto> to = new ArrayList<>();
            FcmClientToken clientToken = clientTokenRepo.findByIdxAndIsActiveIsTrue(order.getOrderer().getIdxFcmToken());
            if(clientToken != null) {
                to.add(FcmTokenDto.builder()
                        .token(clientToken.getToken())
                        .build());
                // fcm build
                FcmRequestDto dto = FcmRequestDto.builder()
                        .title("(" + order.getBoxNumber() + "번) 주문이 정상적으로 접수되었습니다.")
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
            if(order.getOrderer().getOrdererType() == OrdererType.GUEST && order.getOrderer().getPhoneNumber() == null) return;

            Map<String, String> data = new HashMap<>();

            data.put("order_bn", order.getBoxNumber() + "번");
            data.put("order_addr", order.getDeliveryDetailSite().getFullName());
            data.put("order_date", CommonSubService.getOrderDateWithAdditionalTime(order));
            data.put("order_items", CommonSubService.orderItemLongText(order));
            if(order.getOrderer().getUser() != null) {
                OrderApproveTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getUser().getPhoneNumber()), data);
            } else {
                OrderApproveTemplate.send(PhoneNumberFormatter.format(order.getOrderer().getPhoneNumber()), data);
            }
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }
}

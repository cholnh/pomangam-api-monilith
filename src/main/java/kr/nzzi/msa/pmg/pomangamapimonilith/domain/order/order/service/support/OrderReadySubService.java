package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.owner.FcmOwnerTokenJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.owner.FcmOwnerToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service.impl.FcmServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.sub.model.OrderItemSub;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.exception.OrderException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.dao.jpa.OwnerJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.model.Owner;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dao.jpa.ProductJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dao.jpa.ProductSubJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.ProductSub;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dao.jpa.StoreJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.OrderReadyTemplate;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.BootpayApi;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.time.CustomTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderReadySubService{

    FcmServiceImpl fcmService;
    StoreJpaRepository storeRepo;
    ProductJpaRepository productRepo;
    ProductSubJpaRepository productSubRepo;
    FcmOwnerTokenJpaRepository ownerTokenRepo;
    OwnerJpaRepository ownerRepo;
    private static BootpayApi bootpayApi = new BootpayApi();

    public void verifyIsValidVerifyOrder(OrderType orderType) {
        boolean isValid = false;
        switch (orderType) {
            case PAYMENT_READY:
            case PAYMENT_READY_FAIL_POINT:
            case PAYMENT_READY_FAIL_COUPON:
            case PAYMENT_READY_FAIL_PROMOTION:
            case PAYMENT_SUCCESS:
                isValid = true;
                break;
        }

        if(!isValid) {
            throw  new OrderException("invalid order verify.");
        }
    }

    public boolean verifyPG(String receipt_id, int paymentCost) {
        bootpayApi.getAccessToken();
        return bootpayApi.verify(receipt_id, paymentCost);
    }

    public void addCntOrder(Order order) {
        Map<Long, Short> map = new HashMap<>();
        order.getOrderItems().forEach((item) -> {
            short quantity = item.getQuantity();

            // storeMapper
            if(map.containsKey(item.getStore().getIdx())) {
                quantity += map.get(item.getStore().getIdx());
            }
            map.put(item.getStore().getIdx(), quantity);

            // productMapper
            Product product = productRepo.findByIdxAndIsActiveIsTrue(item.getProduct().getIdx());
            product.setCntOrder(product.getCntOrder() + Short.toUnsignedInt(quantity));
            productRepo.save(product);

            // productMapper sub
            for(OrderItemSub sub : item.getOrderItemSubs()) {
                ProductSub productSub = productSubRepo.findByIdxAndIsActiveIsTrue(sub.getIdx());
                productSub.setCntOrder(productSub.getCntOrder() + Short.toUnsignedInt(sub.getQuantity()));
                productSubRepo.save(productSub);

            }
        });
        map.forEach((k, v) -> {
            Store store = storeRepo.findByIdxAndIsActiveIsTrue(k);
            store.setCntOrder(store.getCntOrder() + Short.toUnsignedInt(v));
            storeRepo.save(store);
        });
    }

    public void sendFcm(Order order) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "order_ready");

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
                    .title(CustomTime.format("HH:mm", order.getModifyDate()) + " (" + order.getBoxNumber() + "번) 새로운 주문이 등록되었습니다.")
                    //.body("[no." + order.getIdx() + "] " + CommonSubService.orderItemLongText(order))
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
                    data.put("order_time", CustomTime.format("HH:mm", order.getModifyDate()));
                    data.put("order_idx", "no."+order.getIdx());
                    data.put("order_bn", order.getBoxNumber() + "번");
                    data.put("order_date", CommonSubService.getOrderDateWithAdditionalTime(order));
                    data.put("order_addr", order.getDeliveryDetailSite().getFullName());
                    data.put("order_pn", CommonSubService.getOrdererPhoneNumber(order));
                    data.put("order_items", CommonSubService.orderItemLongText(order, idxStore));
                    OrderReadyTemplate.send(owner.getPhoneNumber(), data);
                }
            }
        } catch (Exception msgException) {
            msgException.printStackTrace();
        }
    }

}

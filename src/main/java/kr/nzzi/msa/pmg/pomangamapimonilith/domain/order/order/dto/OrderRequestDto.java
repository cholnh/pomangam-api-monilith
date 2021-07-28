package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.model.DeliveryDetailSite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.dto.OrderItemRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.model.OrderItem;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.cash_receipt.CashReceiptType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.Orderer;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.OrdererType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.payment_info.PaymentInfo;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.payment_info.PaymentType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.model.OrderTime;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString(exclude = {"user"})
public class OrderRequestDto implements Serializable {

    // 주문 날짜
    private LocalDate orderDate;

    // 주문 시간
    private Long idxOrderTime;

    // 받는 장소
    private Long idxDeliveryDetailSite;

    private Long idxFcmToken;

    // PaymentInfo
    private PaymentType paymentType;
    private Integer usingPoint;
    private String usingCouponCode;
    private Set<Long> idxesUsingCoupons = new HashSet<>();
    private Set<Long> idxesUsingPromotions = new HashSet<>();
    private String cashReceipt;
    private CashReceiptType cashReceiptType;

    List<OrderItemRequestDto> orderItems = new ArrayList<>();

    // Orderer
    private OrdererType ordererType;
    private User user;  // 내부 작성용

    private String vbankName;

    private String note;

    private String phoneNumber;

    public Order toEntity() {
        Order entity = Order.builder()
                .orderType(OrderType.PAYMENT_READY)
                .orderDate(this.orderDate)
                .orderTime(OrderTime.builder()
                        .idx(this.idxOrderTime)
                        .build())
                .deliveryDetailSite(DeliveryDetailSite.builder()
                        .idx(this.idxDeliveryDetailSite)
                        .build())
                .orderer(Orderer.builder()
                        .ordererType(this.ordererType)
                        .idxFcmToken(this.idxFcmToken)
                        .user(this.user)
                        .phoneNumber(this.phoneNumber)
                        .build())
                .paymentInfo(PaymentInfo.builder()
                        .paymentType(this.paymentType)
                        .usingPoint(this.usingPoint)
                        .cashReceipt(this.cashReceipt)
                        .cashReceiptType(this.cashReceiptType)
                        .build())
                .orderItems(convertOrderItem(this.orderItems))
                .note(this.note)
                .build();
        return entity;
    }

    private List<OrderItem> convertOrderItem(List<OrderItemRequestDto> dtos) {
        List<OrderItem> entities = new ArrayList<>();
        if(dtos != null) {
            for(OrderItemRequestDto dto : dtos) {
                if(dto != null) {
                    entities.add(dto.toEntity());
                }
            }
        }
        return entities;
    }
}
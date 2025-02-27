package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.service.support;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.dao.jpa.CouponJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.model.Coupon;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.mapper.dao.jpa.CouponMapperJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.mapper.model.CouponMapper;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.model.OrderItem;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.log.dao.jpa.OrderLogJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.log.model.OrderLog;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.dao.jpa.OrderJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.exception.OrderException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.OrderType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.Orderer;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.orderer.OrdererType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.model.PointType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.service.impl.PointLogServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.mapper.dao.jpa.PromotionMapperJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.mapper.model.PromotionMapper;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.dao.jpa.PromotionJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.model.Promotion;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.service.impl.UserServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.time.CustomTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@AllArgsConstructor
public class CommonSubService {

    OrderJpaRepository orderRepo;
    OrderLogJpaRepository orderLogRepo;
    UserServiceImpl userService;
    CouponJpaRepository couponRepo;
    CouponMapperJpaRepository couponMapperRepo;
    PointLogServiceImpl pointLogService;
    PromotionJpaRepository promotionRepo;
    PromotionMapperJpaRepository promotionMapperRepo;

    public void log(Long idxOrder, OrderType...orderTypes) {
        if(orderTypes == null || orderTypes.length == 0) return;

        // log 상태 변경
        List<OrderLog> orderLogs = new ArrayList<>();
        for(OrderType orderType : orderTypes) {
            orderLogs.add(OrderLog.builder()
                    .idxOrder(idxOrder)
                    .orderType(orderType)
                    .build());
        }
        orderLogRepo.saveAll(orderLogs);

        // order 상태 변경
        Order order = orderRepo.findById(idxOrder)
                .orElseThrow(() -> new RuntimeException("invalid order"));
        order.setOrderType(orderTypes[orderTypes.length-1]); // 최종 상태
        orderRepo.save(order);
    }

    public void verifyUsingPoint(Order order) {
        User user = order.getOrderer().getUser();
        if(user != null) {
            int userPoint = pointLogService.findByIdxUser(user.getIdx());
            int usingPoint = order.getPaymentInfo().getUsingPoint();
            if(usingPoint == 0) return;
            System.out.println(userPoint + " " + usingPoint);
            if(userPoint < usingPoint || usingPoint < 0) {
                log(order.getIdx(), OrderType.PAYMENT_READY_FAIL_POINT);
                throw new OrderException("invalid using point.");
            }
            userService.minusPointByIdx(user.getIdx(), usingPoint, PointType.USED_BY_BUY, order.getIdx());
        }
    }

    public void verifyUsingCoupons(Order order, Set<Long> idxesUsingCoupons) {
        User user = order.getOrderer().getUser();
        if(user != null && idxesUsingCoupons != null && !idxesUsingCoupons.isEmpty()) {
            List<CouponMapper> couponMappers = new ArrayList<>();
            List<Coupon> userCoupons = couponRepo.findByUser_IdxAndIsActiveIsTrue(user.getIdx());
            for(Long idxUsingCoupon : idxesUsingCoupons) {
                Coupon coupon = findCoupon(userCoupons, idxUsingCoupon);
                if(coupon == null || !coupon.isValid()) {
                    log(order.getIdx(), OrderType.PAYMENT_READY_FAIL_COUPON);
                    throw new OrderException("invalid using coupon.");
                }
                coupon.setIsUsed(true);
                couponMappers.add(CouponMapper.builder()
                        .order(order)
                        .coupon(coupon)
                        .build());
            }
            couponMapperRepo.saveAll(couponMappers);
            //order.getPaymentInfo().getUsingCoupons().addAll(couponMappers);
        }
    }


    public void verifyUsingCouponCode(Order order, String couponCode) {
        if(couponCode != null) {
            Optional<Coupon> optional = couponRepo.findByCodeAndIsActiveIsTrueAndUserIsNull(couponCode);
            if(optional.isPresent() && optional.get().isValid()) {
                Coupon coupon = optional.get();
                coupon.setIsUsed(true);
                CouponMapper mapper = CouponMapper.builder()
                        .order(order)
                        .coupon(coupon)
                        .build();
                couponMapperRepo.save(mapper);
                //order.getPaymentInfo().getUsingCoupons().add(mapper);
            } else {
                log(order.getIdx(), OrderType.PAYMENT_READY_FAIL_COUPON);
                throw new OrderException("invalid coupon code.");
            }
        }
    }

    public void verifyUsingPromotions(Order order, Set<Long> idxesUsingPromotions) {
        if(idxesUsingPromotions != null) {
            for(Long pIdx : idxesUsingPromotions) {
                Optional<Promotion> optional = promotionRepo.findById(pIdx);
                if(optional.isPresent() && optional.get().isValid()) {
                    Promotion promotion = optional.get();
                    promotionMapperRepo.save(PromotionMapper.builder()
                            .order(order)
                            .promotion(promotion)
                            .build());
                } else {
                    log(order.getIdx(), OrderType.PAYMENT_READY_FAIL_PROMOTION);
                    throw new OrderException("invalid promotion.");
                }
            }
        }
    }

    public void verifySavedPoint(Order order) {
        User user = order.getOrderer().getUser();
        if(user != null) {
            float percentSavePoint = user.getPointRank().getPercentSavePoint();
            int priceSavePoint = user.getPointRank().getPriceSavePoint();
            int paymentCost = order.paymentCost();
            int savedPoint = (int)(paymentCost * percentSavePoint / 100) + priceSavePoint;
            order.getPaymentInfo().setSavedPoint(savedPoint);
            orderRepo.save(order);
        }
    }

    public Coupon findCoupon(List<Coupon> userCoupons, Long findIdx) {
        for(Coupon userCoupon : userCoupons) {
            if(findIdx.compareTo(userCoupon.getIdx()) == 0) {
                return userCoupon;
            }
        }
        return null;
    }

    public void commitSavedPoint(Order order) {
        User user = order.getOrderer().getUser();
        if(user != null) {
            int savedPoint = order.getPaymentInfo().getSavedPoint();
            userService.plusPointByIdx(user.getIdx(), savedPoint, PointType.ISSUED_BY_BUY, order.getIdx());
        }
    }

    public void rollbackUsingPoint(Order order) {
        User user = order.getOrderer().getUser();
        if(user != null) {
            int usingPoint = order.getPaymentInfo().getUsingPoint();
            if(usingPoint > 0) {
                userService.plusPointByIdx(user.getIdx(), usingPoint, PointType.ROLLBACK_ISSUED_BY_PAYMENT_CANCEL, order.getIdx());
            }
        }
    }

    public void rollbackSavedPoint(Order order) {
        User user = order.getOrderer().getUser();
        if(user != null) {
            int savedPoint = order.getPaymentInfo().getSavedPoint();
            if(savedPoint > 0) {
                userService.minusPointByIdx(user.getIdx(), savedPoint, PointType.ROLLBACK_SAVED_BY_PAYMENT_CANCEL, order.getIdx());
            }
        }
    }

    public void rollbackUsingCoupons(Order order) {
        List<CouponMapper> couponMappers = couponMapperRepo.findByOrder_Idx(order.getIdx());
        for(CouponMapper couponMapper : couponMappers) {
            Coupon coupon = couponMapper.getCoupon();
            coupon.setIsUsed(false);
            couponRepo.save(coupon);
            couponMapperRepo.delete(couponMapper);
            order.getPaymentInfo().usingCouponsClear(); // order 쪽도 변경해줘야 함.
        }
    }

    public static Set<Long> getIdxStores(Order order) {
        Set<Long> idxStores = new HashSet<>();
        for(OrderItem item : order.getOrderItems()) {
            idxStores.add(item.getStore().getIdx());
        }
        return idxStores;
    }

    public static String getOrderTime(Order order) {
        LocalDateTime arriveDateTime = LocalDateTime.of(
                order.getOrderDate(),
                order.getOrderTime().getArrivalTime()
        );
        return CustomTime.format("HH:mm", arriveDateTime);
    }

    public static String getOrderDate(Order order) {
        LocalDateTime arriveDateTime = LocalDateTime.of(
                order.getOrderDate(),
                order.getOrderTime().getArrivalTime()
        );
        return CustomTime.format("yyyy/MM/dd HH:mm", arriveDateTime);
    }

    public static String getOrderDateWithAdditionalTime(Order order) {
        LocalDateTime arriveDateTime = LocalDateTime.of(
                order.getOrderDate(),
                order.getOrderTime().getArrivalTime()
        );
        LocalTime additionalTime = order.getDeliveryDetailSite().getAdditionalTime();
        arriveDateTime = arriveDateTime
                .plusHours(additionalTime.getHour())
                .plusMinutes(additionalTime.getMinute())
                .plusSeconds(additionalTime.getSecond());

        return CustomTime.format("yyyy/MM/dd HH:mm", arriveDateTime);
    }

    public static String getOrdererPhoneNumber(Order order) {
        Orderer orderer = order.getOrderer();
        return orderer.getOrdererType() == OrdererType.USER
                ? orderer.getUser().getPhoneNumber()
                : "미표기";
    }

    public static String orderItemLongText(Order order) {
        return orderItemLongText(order, null);
    }

    public static String orderItemLongText(Order order, Long idxStore) {
        List<OrderItem> items = order.getOrderItems();
        List<OrderItem> storeItems;

        if(idxStore == null) {
            storeItems = items;
        } else {
            storeItems = new ArrayList<>();
            for(OrderItem item : items) {
                if(item.getStore().getIdx().intValue() == idxStore.intValue()) {
                    storeItems.add(item);
                }
            }
        }

        String text = storeItems.get(0).getProduct().getProductInfo().getName();
        int size = storeItems.size();
        if(size == 1) {
            if(storeItems.get(0).getQuantity() != 1) {
                text += " " + storeItems.get(0).getQuantity() + "개";
            }
        } else if(size > 1) {
            text += " 외 " + (storeItems.size() - 1) + "개";
        } else {
            text = "오류";
        }
        return text;
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.model.Coupon;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * coupon - order [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "coupon_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CouponMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_coupon", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_order", nullable = false)
    private Order order;

    @Builder
    public CouponMapper(Coupon coupon, Order order) {
        this.coupon = coupon;
        this.order = order;
    }
}

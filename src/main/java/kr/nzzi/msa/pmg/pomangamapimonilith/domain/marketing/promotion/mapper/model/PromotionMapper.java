package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.Order;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.model.Promotion;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * promotion - order [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "promotion_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PromotionMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_promotion", nullable = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_order", nullable = false)
    private Order order;

    @Builder
    public PromotionMapper(Promotion promotion, Order order) {
        this.promotion = promotion;
        this.order = order;
    }
}

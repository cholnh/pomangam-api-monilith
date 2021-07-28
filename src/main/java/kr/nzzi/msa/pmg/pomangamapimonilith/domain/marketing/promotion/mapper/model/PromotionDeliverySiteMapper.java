package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.model.Promotion;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * promotion - deliverySite [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "promotion_deliverysite_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PromotionDeliverySiteMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_promotion", nullable = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_delivery_site", nullable = false)
    private DeliverySite deliverySite;

    @Builder
    public PromotionDeliverySiteMapper(Promotion promotion, DeliverySite deliverySite) {
        this.promotion = promotion;
        this.deliverySite = deliverySite;
    }
}

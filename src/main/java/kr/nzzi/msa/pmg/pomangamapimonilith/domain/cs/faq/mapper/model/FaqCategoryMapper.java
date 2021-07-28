package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.category.model.FaqCategory;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * faqCategory - deliverySite [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "faq_category_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
public class FaqCategoryMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_faq_category", nullable = false)
    private FaqCategory faqCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_delivery_site", nullable = false)
    private DeliverySite deliverySite;

    @Builder
    public FaqCategoryMapper(FaqCategory faqCategory, DeliverySite deliverySite) {
        this.faqCategory = faqCategory;
        this.deliverySite = deliverySite;
    }
}

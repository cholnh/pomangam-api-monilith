package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_like_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductLike extends Auditable {

    /**
     * 유저 인덱스
     */
    @Column(name="idx_user", nullable = false)
    private Long idxUser;

    /**
     * 제품 인덱스
     */
    @Column(name="idx_product", nullable = false)
    private Long idxProduct;

    @Builder
    public ProductLike(Long idxUser, Long idxProduct) {
        this.idxUser = idxUser;
        this.idxProduct = idxProduct;
    }
}


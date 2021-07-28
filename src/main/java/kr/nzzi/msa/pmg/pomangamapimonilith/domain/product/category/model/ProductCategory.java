package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.category.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_category_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductCategory extends Auditable {

    /**
     * 제품 카테고리 타이틀
     * ex) 메인, 서브, 음료 등
     * 글자수: utf8 기준 / 영문 20자 / 한글 20자
     */
    @Column(name = "category_title", nullable = false, length = 20)
    private String categoryTitle;

    @Builder
    public ProductCategory(Long idx, String categoryTitle) {
        super.setIdx(idx);
        this.categoryTitle = categoryTitle;
    }
}

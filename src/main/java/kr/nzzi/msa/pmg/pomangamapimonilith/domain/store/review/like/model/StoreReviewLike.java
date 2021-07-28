package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "store_review_like_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreReviewLike extends Auditable {

    /**
     * 유저 인덱스
     */
    @Column(name="idx_user", nullable = false)
    private Long idxUser;

    /**
     * 업체 리뷰 인덱스
     */
    @Column(name="idx_store_review", nullable = false)
    private Long idxStoreReview;

    @Builder
    public StoreReviewLike(Long idxUser, Long idxStoreReview) {
        this.idxUser = idxUser;
        this.idxStoreReview = idxStoreReview;
    }
}


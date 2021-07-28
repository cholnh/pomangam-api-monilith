package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.annotation.BooleanToYNConverter;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "store_review_reply_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreReviewReply extends Auditable {

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

    /**
     * 익명 여부
     * default/null: true(Y)
     * 대문자 필수
     */
    @Column(name = "is_anonymous", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isAnonymous;

    /**
     * 리뷰 내용
     * 글자수: utf8 기준 / 영문 300자 / 한글 300자
     */
    @Column(name="contents", nullable = false, length = 300)
    private String contents;

    /**
     * 총 좋아요 개수
     */
    @Column(name = "cnt_like", nullable = false, columnDefinition = "INT default 0")
    private Integer cntLike;

    @PrePersist
    private void prePersist() {
        this.cntLike = 0; // always 0 when its insert
    }

    @Builder
    public StoreReviewReply(Long idxUser, Long idxStoreReview, Boolean isAnonymous, String contents, Integer cntLike) {
        this.idxUser = idxUser;
        this.idxStoreReview = idxStoreReview;
        this.isAnonymous = isAnonymous;
        this.contents = contents;
        this.cntLike = cntLike;
    }

    public StoreReviewReply update(StoreReviewReply from) {
        if(from.getIdxStoreReview() != null) { this.setIdxStoreReview(from.getIdxStoreReview()); }
        if(from.getIsAnonymous() != null) { this.setIsAnonymous(from.getIsAnonymous()); }
        if(from.getContents() != null) { this.setContents(from.getContents()); }
        return this;
    }

    public void addCntLike() {
        if(this.cntLike == null) {
            this.cntLike = 0;
        }
        this.cntLike++;
    }
    public void subCntLike() {
        if(this.cntLike == null) {
            this.cntLike = 0;
        }
        this.cntLike--;
        if(this.cntLike < 0) {
            this.cntLike = 0;
        }
    }
}


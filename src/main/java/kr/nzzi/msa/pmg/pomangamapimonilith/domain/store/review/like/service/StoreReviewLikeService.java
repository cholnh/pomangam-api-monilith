package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.service;

public interface StoreReviewLikeService {
    boolean toggle(String phoneNumber, Long rIdx);
    void like(String phoneNumber, Long rIdx);
    void cancelLike(String phoneNumber, Long rIdx);
}

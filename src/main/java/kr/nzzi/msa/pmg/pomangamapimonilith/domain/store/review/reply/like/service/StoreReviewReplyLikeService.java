package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.service;

public interface StoreReviewReplyLikeService {
    boolean toggle(String phoneNumber, Long srIdx);
    void like(String phoneNumber, Long srIdx);
    void cancelLike(String phoneNumber, Long srIdx);
}

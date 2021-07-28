package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.like.service;

public interface ProductReplyLikeService {
    boolean toggle(String phoneNumber, Long prIdx);
    void like(String phoneNumber, Long prIdx);
    void cancelLike(String phoneNumber, Long prIdx);
}

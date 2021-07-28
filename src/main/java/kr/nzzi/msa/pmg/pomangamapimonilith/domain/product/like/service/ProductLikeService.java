package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.service;

public interface ProductLikeService {
    boolean toggle(String phoneNumber, Long pIdx);
    void like(String phoneNumber, Long pIdx);
    void cancelLike(String phoneNumber, Long pIdx);
}

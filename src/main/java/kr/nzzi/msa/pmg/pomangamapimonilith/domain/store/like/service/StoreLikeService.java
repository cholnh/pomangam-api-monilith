package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.like.service;

public interface StoreLikeService {
    boolean toggle(String phoneNumber, Long sIdx);
    void like(String phoneNumber, Long sIdx);
    void cancelLike(String phoneNumber, Long sIdx);
}

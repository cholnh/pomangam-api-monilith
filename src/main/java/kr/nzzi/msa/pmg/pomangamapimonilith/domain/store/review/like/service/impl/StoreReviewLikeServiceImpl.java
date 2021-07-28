package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.dao.jpa.StoreReviewLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.model.StoreReviewLike;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.service.StoreReviewLikeService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.dao.jpa.StoreReviewJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.model.StoreReview;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StoreReviewLikeServiceImpl implements StoreReviewLikeService {

    StoreReviewLikeJpaRepository storeReviewLikeRepo;
    StoreReviewJpaRepository storeReviewRepo;
    UserJpaRepository userRepo;

    @Override
    @Transactional
    public boolean toggle(String phoneNumber, Long rIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        boolean like = storeReviewLikeRepo.existsByIdxUserAndIdxStoreReview(uIdx, rIdx);
        if(like) {
            cancelLike(uIdx, rIdx);
        } else {
            like(uIdx, rIdx);
        }
        return !like;
    }

    @Override
    @Transactional
    public void like(String phoneNumber, Long rIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        like(uIdx, rIdx);
    }

    @Override
    @Transactional
    public void cancelLike(String phoneNumber, Long rIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        cancelLike(uIdx, rIdx);
    }

    private void like(Long uIdx, Long rIdx) {
        saveLike(uIdx, rIdx);
        addCntLike(rIdx);
    }

    private void cancelLike(Long uIdx, Long rIdx) {
        deleteLike(uIdx, rIdx);
        subCntLike(rIdx);
    }

    private void saveLike(Long uIdx, Long rIdx) {
        StoreReviewLike like = StoreReviewLike.builder()
                .idxStoreReview(rIdx)
                .idxUser(uIdx)
                .build();
        storeReviewLikeRepo.save(like);
    }

    private void deleteLike(Long uIdx, Long rIdx) {
        storeReviewLikeRepo.deleteByIdxUserAndIdxStoreReviewQuery(uIdx, rIdx);
    }

    private void addCntLike(Long rIdx) {
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(rIdx);
        storeReview.addCntLike();
        storeReviewRepo.save(storeReview);
    }
    private void subCntLike(Long rIdx) {
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(rIdx);
        storeReview.subCntLike();
        storeReviewRepo.save(storeReview);
    }
}

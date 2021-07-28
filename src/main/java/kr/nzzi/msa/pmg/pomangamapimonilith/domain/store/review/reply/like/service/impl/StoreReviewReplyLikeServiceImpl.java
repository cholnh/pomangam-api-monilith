package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.dao.jpa.StoreReviewReplyLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.model.StoreReviewReplyLike;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.service.StoreReviewReplyLikeService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.dao.jpa.StoreReviewReplyJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.model.StoreReviewReply;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StoreReviewReplyLikeServiceImpl implements StoreReviewReplyLikeService {

    StoreReviewReplyLikeJpaRepository storeReviewReplyLikeRepo;
    StoreReviewReplyJpaRepository storeReviewReplyRepo;
    UserJpaRepository userRepo;

    @Override
    @Transactional
    public boolean toggle(String phoneNumber, Long srIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        boolean like = storeReviewReplyLikeRepo.existsByIdxUserAndIdxStoreReviewReply(uIdx, srIdx);
        if(like) {
            cancelLike(uIdx, srIdx);
        } else {
            like(uIdx, srIdx);
        }
        return !like;
    }

    @Override
    @Transactional
    public void like(String phoneNumber, Long srIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        like(uIdx, srIdx);
    }

    @Override
    @Transactional
    public void cancelLike(String phoneNumber, Long srIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        cancelLike(uIdx, srIdx);
    }

    private void like(Long uIdx, Long srIdx) {
        saveLike(uIdx, srIdx);
        addCntLike(srIdx);
    }

    private void cancelLike(Long uIdx, Long srIdx) {
        deleteLike(uIdx, srIdx);
        subCntLike(srIdx);
    }

    private void saveLike(Long uIdx, Long srIdx) {
        StoreReviewReplyLike like = StoreReviewReplyLike.builder()
                .idxStoreReviewReply(srIdx)
                .idxUser(uIdx)
                .build();
        storeReviewReplyLikeRepo.save(like);
    }

    private void deleteLike(Long uIdx, Long srIdx) {
        storeReviewReplyLikeRepo.deleteByIdxUserAndIdxStoreReviewReplyQuery(uIdx, srIdx);
    }

    private void addCntLike(Long srIdx) {
        StoreReviewReply storeReviewReply = storeReviewReplyRepo.findByIdxAndIsActiveIsTrue(srIdx);
        storeReviewReply.addCntLike();
        storeReviewReplyRepo.save(storeReviewReply);
    }
    private void subCntLike(Long srIdx) {
        StoreReviewReply storeReviewReply = storeReviewReplyRepo.findByIdxAndIsActiveIsTrue(srIdx);
        storeReviewReply.subCntLike();
        storeReviewReplyRepo.save(storeReviewReply);
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.like.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.like.dao.jpa.ProductReplyLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.like.model.ProductReplyLike;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.like.service.ProductReplyLikeService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.dao.jpa.ProductReplyJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.model.ProductReply;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductReplyLikeServiceImpl implements ProductReplyLikeService {

    ProductReplyLikeJpaRepository productReplyLikeRepo;
    ProductReplyJpaRepository productReplyRepo;
    UserJpaRepository userRepo;

    @Override
    @Transactional
    public boolean toggle(String phoneNumber, Long prIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        boolean like = productReplyLikeRepo.existsByIdxUserAndIdxProductReply(uIdx, prIdx);
        if(like) {
            cancelLike(uIdx, prIdx);
        } else {
            like(uIdx, prIdx);
        }
        return !like;
    }

    @Override
    @Transactional
    public void like(String phoneNumber, Long prIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        like(uIdx, prIdx);
    }

    @Override
    @Transactional
    public void cancelLike(String phoneNumber, Long prIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        cancelLike(uIdx, prIdx);
    }

    private void like(Long uIdx, Long prIdx) {
        saveLike(uIdx, prIdx);
        addCntLike(prIdx);
    }

    private void cancelLike(Long uIdx, Long prIdx) {
        deleteLike(uIdx, prIdx);
        subCntLike(prIdx);
    }

    private void saveLike(Long uIdx, Long prIdx) {
        ProductReplyLike like = ProductReplyLike.builder()
                .idxProductReply(prIdx)
                .idxUser(uIdx)
                .build();
        productReplyLikeRepo.save(like);
    }

    private void deleteLike(Long uIdx, Long prIdx) {
        productReplyLikeRepo.deleteByIdxUserAndIdxProductReplyQuery(uIdx, prIdx);
    }

    private void addCntLike(Long prIdx) {
        ProductReply productReply = productReplyRepo.findByIdxAndIsActiveIsTrue(prIdx);
        productReply.addCntLike();
        productReplyRepo.save(productReply);
    }
    private void subCntLike(Long prIdx) {
        ProductReply productReply = productReplyRepo.findByIdxAndIsActiveIsTrue(prIdx);
        productReply.subCntLike();
        productReplyRepo.save(productReply);
    }
}

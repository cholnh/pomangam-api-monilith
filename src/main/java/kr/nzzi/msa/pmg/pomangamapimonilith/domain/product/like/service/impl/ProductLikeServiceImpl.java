package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.dao.jpa.ProductLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.model.ProductLike;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.service.ProductLikeService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dao.jpa.ProductJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

    ProductLikeJpaRepository productLikeRepo;
    ProductJpaRepository productRepo;
    UserJpaRepository userRepo;

    @Override
    @Transactional
    public boolean toggle(String phoneNumber, Long pIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        boolean like = productLikeRepo.existsByIdxUserAndIdxProduct(uIdx, pIdx);
        if(like) {
            cancelLike(uIdx, pIdx);
        } else {
            like(uIdx, pIdx);
        }
        return !like;
    }

    @Override
    @Transactional
    public void like(String phoneNumber, Long pIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        like(uIdx, pIdx);
    }

    @Override
    @Transactional
    public void cancelLike(String phoneNumber, Long pIdx) {
        Long uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        cancelLike(uIdx, pIdx);
    }

    private void like(Long uIdx, Long pIdx) {
        saveLike(uIdx, pIdx);
        addCntLike(pIdx);
    }

    private void cancelLike(Long uIdx, Long pIdx) {
        deleteLike(uIdx, pIdx);
        subCntLike(pIdx);
    }

    private void saveLike(Long uIdx, Long pIdx) {
        ProductLike like = ProductLike.builder()
                .idxProduct(pIdx)
                .idxUser(uIdx)
                .build();

        productLikeRepo.save(like);
    }

    private void deleteLike(Long uIdx, Long pIdx) {
        productLikeRepo.deleteByIdxUserAndIdxProductQuery(uIdx, pIdx);
    }

    private void addCntLike(Long pIdx) {
        Product product = productRepo.findByIdxAndIsActiveIsTrue(pIdx);
        product.addCntLike();
        productRepo.save(product);
    }
    private void subCntLike(Long pIdx) {
        Product product = productRepo.findByIdxAndIsActiveIsTrue(pIdx);
        product.subCntLike();
        productRepo.save(product);
    }
}

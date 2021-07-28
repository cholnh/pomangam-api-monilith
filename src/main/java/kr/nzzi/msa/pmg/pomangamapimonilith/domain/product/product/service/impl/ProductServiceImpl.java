package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.like.dao.jpa.ProductLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dao.jpa.ProductJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto.ProductDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto.ProductSummaryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.service.ProductService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.service.impl.ProductReplyServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dto.ProductSubCategoryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dao.jpa.ProductSubJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductJpaRepository productRepo;
    UserJpaRepository userRepo;
    ProductLikeJpaRepository productLikeRepo;
    ProductReplyServiceImpl productReplyService;
    ProductSubJpaRepository productSubRepo;

    /**
     * 업체에 해당되는 제품 리스트 검색
     * 제품 캐싱 적용
     *
     * @param sIdx 업체 인덱스
     * @return 업체 인덱스에 해당하는 제품(리스트) 반환
     */
    @Cacheable(value = "product", key="#sIdx")
    public List<ProductDto> findByIdxStore(Long sIdx) {
        List<Product> products = productRepo.findByIdxStoreAndIsActiveIsTrueOrderBySequenceAsc(sIdx);
        return ProductDto.fromEntities(products);
    }

    /**
     * 업체에 해당되는 제품 요약 리스트 검색 (페이징 처리 용)
     *
     * @param sIdx 업체 인덱스
     * @param pageable
     * @return 제품 요약(리스트) 반환
     */
    @Override
    public List<ProductSummaryDto> findSummaryByIdxStore(Long sIdx, Pageable pageable) {
        List<Product> products = productRepo.findByIdxStoreAndIsActiveIsTrueOrderBySequenceAsc(sIdx, pageable).getContent();
        return ProductSummaryDto.fromEntities(products);
    }

    /**
     * 카테고리에 해당하는 제품 요약 리스트 검색
     *
     * @param cIdx 제품 카테고리 인덱스
     * @param pageable
     * @return 제품 카테고리 인덱스에 해당하는 제품 요약(리스트) 반환
     */
    @Override
    public List<ProductSummaryDto> findSummaryByIdxProductCategory(Long cIdx, Pageable pageable) {
        List<Product> products = productRepo.findByProductCategory_IdxAndIsActiveIsTrueOrderBySequenceAsc(cIdx, pageable).getContent();
        return ProductSummaryDto.fromEntities(products);
    }

    /**
     * 제품 인덱스에 해당하는 제품 검색
     *
     * @param idx 제품 인덱스
     * @param phoneNumber 유저 핸드폰번호 (비회원시 null)
     * @return 제품 인덱스에 해당하는 제품 반환
     */
    @Override
    public ProductDto findByIdx(Long idx, String phoneNumber) {
        Product entity = productRepo.findByIdxAndIsActiveIsTrue(idx);
        ProductDto dto = ProductDto.fromEntity(entity);

        // isLike
        Long uIdx = null;
        boolean isLike = false;
        if(phoneNumber != null) {
            // 회원의 경우 제품 좋아요 유/무 판별
            uIdx = userRepo.findIdxByPhoneNumberAndIsActiveIsTrue(phoneNumber);
            isLike = productLikeRepo.existsByIdxUserAndIdxProduct(uIdx, idx);
        }
        dto.setIsLike(isLike);

        // reply preview
        if(dto.getCntReply() > 0) {
            dto.setReplies(productReplyService.findByIdxProduct(dto.getIdx(), uIdx,
                PageRequest.of(
                0, // page
                 2, // size
                      Sort.by(
                        Sort.Order.desc("cntLike"),         // 좋아요 내림차순 정렬
                        Sort.Order.desc("registerDate")     // 등록날짜 정렬
                    )
                )
            ));
        }

        // product sub category
        dto.setProductSubCategories(ProductSubCategoryDto
                .fromEntities(productSubRepo.findCategoryByIdxProductAndIsActiveIsTrue(dto.getIdx())));

        return dto;
    }

    /**
     * 업체 인덱스에 해당하는 총 개수 반환
     *
     * @param sIdx 업체 인덱스
     * @return 업체 인덱스에 해당하는 총 개수 반환
     */
    @Override
    public long countByIdxStore(Long sIdx) {
        return productRepo.countByIdxStoreAndIsActiveIsTrue(sIdx);
    }

    /**
     * 카테고리에 해당하는 총 개수 반환
     *
     * @param cIdx 카테고리 인덱스
     * @return 카테고리에 해당하는 총 개수 반환
     */
    @Override
    public long countByIdxProductCategory(Long cIdx) {
        return productRepo.countByProductCategory_IdxAndIsActiveIsTrue(cIdx);
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dao.jpa.ProductSubCategoryJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dto.ProductSubCategoryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.model.ProductSubCategory;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dao.jpa.ProductSubJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dto.ProductSubDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.ProductSub;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.service.ProductSubService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductSubServiceImpl implements ProductSubService {

    ProductSubJpaRepository productSubRepo;
    ProductSubCategoryJpaRepository productSubCategoryRepo;

    @Override
    public List<ProductSubCategoryDto> findByIdxProduct(Long pIdx) {
        List<ProductSubCategory> productSubCategories = productSubRepo.findByIdxProductAndIsActiveIsTrue(pIdx);
        return ProductSubCategoryDto.fromEntities(productSubCategories);
    }

    @Override
    public List<ProductSubCategoryDto> findByIdxProductSubCategory(Long cIdx) {
        List<ProductSubCategory> productSubCategories = productSubCategoryRepo.findByIdxAndIsActiveIsTrue(cIdx);
        return ProductSubCategoryDto.fromEntities(productSubCategories);
    }

    @Override
    public ProductSubDto findByIdx(Long idx) {
        ProductSub entity = productSubRepo.findByIdxAndIsActiveIsTrue(idx);
        return ProductSubDto.fromEntity(entity);
    }

    @Override
    public long count() {
        return productSubRepo.countByIsActiveIsTrue();
    }

}

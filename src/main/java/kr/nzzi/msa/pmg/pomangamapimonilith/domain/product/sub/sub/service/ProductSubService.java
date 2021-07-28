package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dto.ProductSubCategoryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dto.ProductSubDto;
import java.util.List;

public interface ProductSubService {
    List<ProductSubCategoryDto> findByIdxProduct(Long pIdx);
    List<ProductSubCategoryDto> findByIdxProductSubCategory(Long cIdx);
    ProductSubDto findByIdx(Long idx);
    long count();
}

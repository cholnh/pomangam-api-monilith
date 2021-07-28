package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto.ProductDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto.ProductSummaryDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    List<ProductSummaryDto> findSummaryByIdxStore(Long sIdx, Pageable pageable);
    List<ProductSummaryDto> findSummaryByIdxProductCategory(Long cIdx, Pageable pageable);
    ProductDto findByIdx(Long idx, String phoneNumber);
    long countByIdxStore(Long sIdx);
    long countByIdxProductCategory(Long cIdx);
}

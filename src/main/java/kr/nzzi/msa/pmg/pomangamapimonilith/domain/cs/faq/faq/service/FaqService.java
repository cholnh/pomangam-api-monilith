package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.faq.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.category.dto.FaqCategoryDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FaqService {
    List<FaqCategoryDto> findByIdxDeliverySite(Long dIdx, Pageable pageable);
}

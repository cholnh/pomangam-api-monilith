package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.faq.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.category.dto.FaqCategoryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.category.model.FaqCategory;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.faq.dao.jpa.FaqJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.faq.service.FaqService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {

    private FaqJpaRepository faqRepo;

    @Override
    public List<FaqCategoryDto> findByIdxDeliverySite(Long dIdx, Pageable pageable) {
        List<FaqCategory> faqs = faqRepo.findFetchJoinByIdxDeliverySiteAndIsActiveIsTrue(dIdx, pageable).getContent();
        return FaqCategoryDto.fromEntities(faqs);
    }
}
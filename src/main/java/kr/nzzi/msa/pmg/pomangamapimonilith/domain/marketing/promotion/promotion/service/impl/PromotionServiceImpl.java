package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.dao.jpa.PromotionJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.dto.PromotionDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    PromotionJpaRepository promotionJpaRepository;

    public List<PromotionDto> findByIdxDeliverySite(Long dIdx) {
        return PromotionDto.fromEntities(promotionJpaRepository.findByIdxDeliverySite(dIdx));
    }
}

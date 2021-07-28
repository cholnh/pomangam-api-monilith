package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.dao.jpa.AdvertisementJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.dto.AdvertisementDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.model.Advertisement;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.service.AdvertisementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private AdvertisementJpaRepository advertisementRepo;

    @Override
    public List<AdvertisementDto> findByIdxDeliverySite(Long dIdx, Pageable pageable) {
        List<Advertisement> advertisements = advertisementRepo.findFetchJoinByIdxDeliverySiteAndIsActiveIsTrue(dIdx, pageable).getContent();
        return AdvertisementDto.fromEntities(advertisements);
    }
}

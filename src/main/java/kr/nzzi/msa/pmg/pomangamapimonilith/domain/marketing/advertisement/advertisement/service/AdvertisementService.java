package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.advertisement.advertisement.dto.AdvertisementDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDto> findByIdxDeliverySite(Long dIdx, Pageable pageable);
}

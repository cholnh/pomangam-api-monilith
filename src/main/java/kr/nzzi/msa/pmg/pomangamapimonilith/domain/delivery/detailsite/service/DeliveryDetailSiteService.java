package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.dto.DeliveryDetailSiteDto;
import java.util.List;

public interface DeliveryDetailSiteService {
    List<DeliveryDetailSiteDto> findByIdxDeliverySite(Long dIdx);
    DeliveryDetailSiteDto findByIdx(Long idx);
    long count();
}

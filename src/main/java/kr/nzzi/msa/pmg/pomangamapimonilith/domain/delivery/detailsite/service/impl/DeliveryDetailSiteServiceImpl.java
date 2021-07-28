package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.dao.jpa.DeliveryDetailSiteJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.dto.DeliveryDetailSiteDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.model.DeliveryDetailSite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.service.DeliveryDetailSiteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryDetailSiteServiceImpl implements DeliveryDetailSiteService {

    DeliveryDetailSiteJpaRepository detailSiteRepo;

    @Override
    public List<DeliveryDetailSiteDto> findByIdxDeliverySite(Long dIdx) {
        List<DeliveryDetailSite> deliveryDetailSites = detailSiteRepo.findByDeliverySite_IdxAndIsActiveIsTrue(dIdx);
        return DeliveryDetailSiteDto.fromEntities(deliveryDetailSites);
    }

    @Override
    public DeliveryDetailSiteDto findByIdx(Long idx) {
        DeliveryDetailSite entity = detailSiteRepo.findByIdxAndIsActiveIsTrue(idx);
        return DeliveryDetailSiteDto.fromEntity(entity);
    }

    @Override
    public long count() {
        return detailSiteRepo.countByIsActiveIsTrue();
    }
}

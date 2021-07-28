package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.dao.jpa.DeliverySiteJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.dto.DeliverySiteDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.service.DeliverySiteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliverySiteServiceImpl implements DeliverySiteService {

    DeliverySiteJpaRepository deliverySiteRepo;

    @Override
    public List<DeliverySiteDto> findAll(Pageable pageable) {
        List<DeliverySite> deliverySites = deliverySiteRepo.findAllByIsActiveIsTrue(pageable).getContent();
        return DeliverySiteDto.fromEntities(deliverySites);
    }

    public List<DeliverySiteDto> findAllByIdxStore(Long sIdx) {
        List<DeliverySite> deliverySites = deliverySiteRepo.findAllByIdxStore(sIdx);
        return DeliverySiteDto.fromEntities(deliverySites);
    }

    @Override
    public DeliverySiteDto findByIdx(Long dIdx) {
        DeliverySite entity = deliverySiteRepo.findByIdxAndIsActiveIsTrue(dIdx);
        return DeliverySiteDto.fromEntity(entity);
    }

    @Override
    public long count() {
        return deliverySiteRepo.countByIsActiveIsTrue();
    }

    @Override
    public List<DeliverySiteDto> search(String query) {
        List<DeliverySite> entity = deliverySiteRepo.search(query);
        return DeliverySiteDto.fromEntities(entity);
    }
}

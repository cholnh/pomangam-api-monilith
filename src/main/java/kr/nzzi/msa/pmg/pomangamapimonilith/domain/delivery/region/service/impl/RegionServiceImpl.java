package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.dao.jpa.RegionJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.dto.RegionDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.model.Region;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    RegionJpaRepository regionRepo;

    @Override
    public List<RegionDto> findAll(Pageable pageable) {
        List<Region> regions = regionRepo.findAllByIsActiveIsTrue(pageable).getContent();
        return RegionDto.fromEntities(regions);
    }

    @Override
    public RegionDto findByIdx(Long idx) {
        Region entity = regionRepo.findByIdxAndIsActiveIsTrue(idx);
        return RegionDto.fromEntity(entity);
    }

    @Override
    public long count() {
        return regionRepo.countByIsActiveIsTrue();
    }
}

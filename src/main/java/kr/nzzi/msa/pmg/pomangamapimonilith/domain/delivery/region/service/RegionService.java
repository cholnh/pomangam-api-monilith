package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.dto.RegionDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RegionService {
    List<RegionDto> findAll(Pageable pageable);
    RegionDto findByIdx(Long idx);
    long count();
}

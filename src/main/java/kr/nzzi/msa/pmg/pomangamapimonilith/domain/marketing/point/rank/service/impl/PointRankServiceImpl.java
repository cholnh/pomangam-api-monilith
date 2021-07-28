package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.dao.jpa.PointRankJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.dto.PointRankDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.service.PointRankService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointRankServiceImpl implements PointRankService {

    PointRankJpaRepository pointRankRepo;

    public List<PointRankDto> findAll() {
        return PointRankDto.fromEntities(pointRankRepo.findAll());
    }
}

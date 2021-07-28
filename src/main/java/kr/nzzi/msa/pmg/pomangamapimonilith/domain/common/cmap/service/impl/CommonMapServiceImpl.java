package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.dao.jpa.CommonMapJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.model.CommonMap;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.service.CommonMapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommonMapServiceImpl implements CommonMapService {

    CommonMapJpaRepository commonMapRepo;

    @Override
    public List<CommonMap> findAllByKey(String key) {
        return commonMapRepo.findByKeyAndIsActiveIsTrue(key);
    }

    @Override
    public String findValueByKey(String key) {
        List<CommonMap> maps = commonMapRepo.findByKeyAndIsActiveIsTrue(key);
        if(maps == null || maps.isEmpty()) {
            return null;
        } else {
            return maps.get(0).getValue();
        }
    }
}

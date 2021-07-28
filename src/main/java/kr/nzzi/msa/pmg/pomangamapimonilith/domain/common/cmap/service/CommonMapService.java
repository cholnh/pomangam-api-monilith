package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.model.CommonMap;

import java.util.List;

public interface CommonMapService {
    List<CommonMap> findAllByKey(String key);
    String findValueByKey(String key);
}

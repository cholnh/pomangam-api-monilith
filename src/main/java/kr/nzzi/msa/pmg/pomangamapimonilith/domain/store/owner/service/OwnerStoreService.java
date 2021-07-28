package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto.StoreDto;

public interface OwnerStoreService {
    StoreDto findByIdx(Long idx);
    StoreDto patch(StoreDto request);
}

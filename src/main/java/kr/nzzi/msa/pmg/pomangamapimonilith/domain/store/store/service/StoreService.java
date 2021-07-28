package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto.StoreDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto.StoreQuantityOrderableDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto.StoreSummaryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.SortType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StoreService {
    List<StoreDto> findByIdxDeliverySite(Long dIdx, Pageable pageable);
    StoreDto findByIdx(Long idx, String phoneNumber);
    long count();
    List<StoreSummaryDto> findOpeningStores(Long dIdx, Long oIdx, LocalDate oDate, Pageable pageable, SortType sortType);
    long countOpeningStores(Long dIdx, Long oIdx, LocalDate oDate);
    List<StoreQuantityOrderableDto> findQuantityOrderableByIdxes(Long dIdx, Long oIdx, LocalDate oDate, List<Long> sIdxes);
}

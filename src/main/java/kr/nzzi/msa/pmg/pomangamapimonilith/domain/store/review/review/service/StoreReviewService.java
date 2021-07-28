package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.dto.StoreReviewDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.model.StoreReviewSortType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreReviewService {
    List<StoreReviewDto> findByIdxStore(Long sIdx, Long uIdx, StoreReviewSortType sortType, Pageable pageable);
    StoreReviewDto findByIdx(Long idx, Long uIdx);
    long count();
    StoreReviewDto save(StoreReviewDto dto, List<MultipartFile> images, String idxesOrderItem);
    StoreReviewDto update(StoreReviewDto dto, List<MultipartFile> images);
    void delete(Long dIdx, Long sIdx, Long idx);
}

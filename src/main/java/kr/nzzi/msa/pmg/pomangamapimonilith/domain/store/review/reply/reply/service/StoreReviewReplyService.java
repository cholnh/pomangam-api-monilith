package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.dto.StoreReviewReplyDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StoreReviewReplyService {
    List<StoreReviewReplyDto> findByIdxStoreReview(Long rIdx, Long uIdx, Pageable pageable);
    StoreReviewReplyDto findByIdx(Long idx, Long uIdx);
    long count();
    StoreReviewReplyDto save(StoreReviewReplyDto dto);
    StoreReviewReplyDto update(StoreReviewReplyDto dto);
    void delete(Long rIdx, Long idx);
}

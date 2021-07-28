package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.dto.NoticeDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.dto.NoticeViewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    List<NoticeViewDto> findByIdxDeliverySiteWithoutContents(Long dIdx, Pageable pageable);
    NoticeDto findByIdx(Long nIdx);
}

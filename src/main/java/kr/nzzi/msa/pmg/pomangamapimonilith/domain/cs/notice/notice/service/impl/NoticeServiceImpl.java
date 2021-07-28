package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.dao.jpa.NoticeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.dto.NoticeDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.dto.NoticeViewDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.exception.NoticeException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.model.Notice;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.notice.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private NoticeJpaRepository noticeRepo;

    @Override
    public List<NoticeViewDto> findByIdxDeliverySiteWithoutContents(Long dIdx, Pageable pageable) {
        List<Notice> notices = noticeRepo.findFetchJoinByIdxDeliverySiteAndIsActiveIsTrue(dIdx, pageable).getContent();
        return NoticeViewDto.fromEntities(notices);
    }

    @Override
    public NoticeDto findByIdx(Long nIdx) {
        return NoticeDto.fromEntity(noticeRepo
                .findByIdxAndIsActiveIsTrue(nIdx)
                .orElseThrow(() -> new NoticeException("invalid event index.")));
    }
}
package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.dto.EventDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.dto.EventViewDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EventService {
    List<EventViewDto> findByIdxDeliverySiteWithoutContents(Long dIdx, Pageable pageable);
    EventDto findByIdx(Long eIdx);
}

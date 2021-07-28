package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.dao.jpa.EventJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.dto.EventDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.dto.EventViewDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.exception.EventException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.model.Event;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private EventJpaRepository eventRepo;

    @Override
    public List<EventViewDto> findByIdxDeliverySiteWithoutContents(Long dIdx, Pageable pageable) {
        List<Event> events = eventRepo.findFetchJoinByIdxDeliverySiteAndIsActiveIsTrue(dIdx, pageable).getContent();
        return EventViewDto.fromEntities(events);
    }

    @Override
    public EventDto findByIdx(Long eIdx) {
        return EventDto.fromEntity(eventRepo
                .findByIdxAndIsActiveIsTrue(eIdx)
                .orElseThrow(() -> new EventException("invalid event index.")));
    }
}
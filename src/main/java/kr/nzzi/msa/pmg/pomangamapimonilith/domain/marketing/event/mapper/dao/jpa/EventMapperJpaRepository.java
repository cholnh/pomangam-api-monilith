package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.mapper.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.mapper.model.EventMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface EventMapperJpaRepository extends JpaRepository<EventMapper, Long>, EventMapperCustomRepository {

}

interface EventMapperCustomRepository {

}

@Transactional(readOnly = true)
class EventMapperCustomRepositoryImpl extends QuerydslRepositorySupport implements EventMapperCustomRepository {

    public EventMapperCustomRepositoryImpl() {
        super(EventMapper.class);
    }


}

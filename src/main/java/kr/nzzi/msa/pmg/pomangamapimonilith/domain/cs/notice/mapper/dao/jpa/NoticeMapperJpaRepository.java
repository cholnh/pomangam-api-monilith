package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.mapper.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.notice.mapper.model.NoticeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface NoticeMapperJpaRepository extends JpaRepository<NoticeMapper, Long>, NoticeMapperCustomRepository {

}

interface NoticeMapperCustomRepository {

}

@Transactional(readOnly = true)
class NoticeMapperCustomRepositoryImpl extends QuerydslRepositorySupport implements NoticeMapperCustomRepository {

    public NoticeMapperCustomRepositoryImpl() {
        super(NoticeMapper.class);
    }


}

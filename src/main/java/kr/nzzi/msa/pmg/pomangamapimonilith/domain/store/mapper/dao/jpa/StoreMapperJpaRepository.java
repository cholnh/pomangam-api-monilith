package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.mapper.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.mapper.model.StoreMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
public interface StoreMapperJpaRepository extends JpaRepository<StoreMapper, Long>, StoreMapperCustomRepository {

}

interface StoreMapperCustomRepository {

}

@Transactional(readOnly = true)
class StoreMapperCustomRepositoryImpl extends QuerydslRepositorySupport implements StoreMapperCustomRepository {

    public StoreMapperCustomRepositoryImpl() {
        super(StoreMapper.class);
    }


}
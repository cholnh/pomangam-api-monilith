package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.mapper.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.faq.mapper.model.FaqCategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface FaqCategoryMapperJpaRepository extends JpaRepository<FaqCategoryMapper, Long>, FaqCategoryMapperCustomRepository {

}

interface FaqCategoryMapperCustomRepository {

}

@Transactional(readOnly = true)
class FaqCategoryMapperCustomRepositoryImpl extends QuerydslRepositorySupport implements FaqCategoryMapperCustomRepository {

    public FaqCategoryMapperCustomRepositoryImpl() {
        super(FaqCategoryMapper.class);
    }


}
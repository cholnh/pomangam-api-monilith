package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.model.PointRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface PointRankJpaRepository extends JpaRepository<PointRank, Long>, PointRankCustomRepository {

}

interface PointRankCustomRepository {

}

@Transactional(readOnly = true)
class PointRankCustomRepositoryImpl extends QuerydslRepositorySupport implements PointRankCustomRepository {

    public PointRankCustomRepositoryImpl() {
        super(PointRank.class);
    }


}

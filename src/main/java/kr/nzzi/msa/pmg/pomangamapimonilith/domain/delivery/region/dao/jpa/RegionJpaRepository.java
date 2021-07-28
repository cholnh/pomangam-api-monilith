package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface RegionJpaRepository extends JpaRepository<Region, Long>, RegionCustomRepository {

    /**
     * deliverySites 사용시에 N+1 문제가 발생하므로, 필수로 FetchJoin 사용할 것.
     *
     * @param pageable
     * @return
     */
    Page<Region> findAllByIsActiveIsTrue(Pageable pageable);
    Region findByIdxAndIsActiveIsTrue(Long idx);
    long countByIsActiveIsTrue();
}

interface RegionCustomRepository {

}

@Transactional(readOnly = true)
class RegionCustomRepositoryImpl extends QuerydslRepositorySupport implements RegionCustomRepository {

    public RegionCustomRepositoryImpl() {
        super(Region.class);
    }


}

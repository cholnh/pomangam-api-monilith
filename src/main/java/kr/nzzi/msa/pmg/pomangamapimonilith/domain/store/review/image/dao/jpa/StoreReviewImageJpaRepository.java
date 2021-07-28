package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.image.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.image.model.StoreReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface StoreReviewImageJpaRepository extends JpaRepository<StoreReviewImage, Long>, StoreReviewImageCustomRepository {

}

interface StoreReviewImageCustomRepository {

}

@Transactional(readOnly = true)
class StoreReviewImageCustomRepositoryImpl extends QuerydslRepositorySupport implements StoreReviewImageCustomRepository {

    public StoreReviewImageCustomRepositoryImpl() {
        super(StoreReviewImage.class);
    }

}
package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.mapper.model.QPromotionDeliverySiteMapper;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.model.Promotion;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.model.QPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PromotionJpaRepository extends JpaRepository<Promotion, Long>, PromotionCustomRepository {

}

interface PromotionCustomRepository {
    List<Promotion> findByIdxDeliverySite(Long dIdx);
}

@Transactional(readOnly = true)
class PromotionCustomRepositoryImpl extends QuerydslRepositorySupport implements PromotionCustomRepository {

    public PromotionCustomRepositoryImpl() {
        super(Promotion.class);
    }

    @Override
    public List<Promotion> findByIdxDeliverySite(Long dIdx) {
        final QPromotionDeliverySiteMapper mapper = QPromotionDeliverySiteMapper.promotionDeliverySiteMapper;
        final QPromotion promotion = QPromotion.promotion;
        return from(promotion)
                .select(promotion)
                .join(mapper).on(promotion.idx.eq(mapper.promotion.idx))
                .where(mapper.deliverySite.idx.eq(dIdx)
                        .and(mapper.isActive.isTrue())
                        .and(mapper.deliverySite.isActive.isTrue())
                        .and(promotion.isActive.isTrue())
                        .and(mapper.deliverySite.idx.eq(dIdx)))
                .fetch();
    }

}

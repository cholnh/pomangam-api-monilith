package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.model.ProductSubCategory;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.mapper.model.QProductSubMapper;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.ProductSub;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.QProductSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ProductSubJpaRepository extends JpaRepository<ProductSub, Long>, ProductSubCustomRepository {
    ProductSub findByIdxAndIsActiveIsTrue(Long idx);
    //Page<Product> findByProductCategory_IdxAndIsActiveIsTrueOrderBySequenceAsc(Long idxProductCategory, Pageable pageable);
    //List<ProductSub> findByProductSubCategory_IdxAndIsActiveIsTrueOrderBySequenceAsc(Long cIdx);
    long countByIsActiveIsTrue();
}

interface ProductSubCustomRepository {
    List<ProductSubCategory> findByIdxProductAndIsActiveIsTrue(Long pIdx);
    List<ProductSubCategory> findCategoryByIdxProductAndIsActiveIsTrue(Long pIdx);
}

@Transactional(readOnly = true)
class ProductSubCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductSubCustomRepository {

    public ProductSubCustomRepositoryImpl() {
        super(ProductSub.class);
    }

    @Override
    public List<ProductSubCategory> findByIdxProductAndIsActiveIsTrue(Long pIdx) {
        final QProductSubMapper mapper = QProductSubMapper.productSubMapper;
        final QProductSub productSub = QProductSub.productSub;
        return from(productSub)
                .select(productSub.productSubCategory)
                .join(mapper).on(productSub.idx.eq(mapper.productSub.idx))
                .fetchJoin()
                .where(mapper.product.idx.eq(pIdx)
                .and(mapper.isActive.isTrue())
                .and(productSub.isActive.isTrue()))
                .orderBy(productSub.sequence.asc())
                .fetch();
    }

    @Override
    public List<ProductSubCategory> findCategoryByIdxProductAndIsActiveIsTrue(Long pIdx) {
        final QProductSubMapper mapper = QProductSubMapper.productSubMapper;
        final QProductSub productSub = QProductSub.productSub;
        return from(productSub)
                .select(productSub.productSubCategory).distinct()
                .join(mapper).on(productSub.idx.eq(mapper.productSub.idx))
                .fetchJoin()
                .where(mapper.product.idx.eq(pIdx)
                .and(mapper.isActive.isTrue())
                .and(productSub.isActive.isTrue()))
                .fetch();
    }
}
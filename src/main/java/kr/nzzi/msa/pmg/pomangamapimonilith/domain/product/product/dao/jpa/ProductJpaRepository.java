package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
    List<Product> findByIdxStoreAndIsActiveIsTrueOrderBySequenceAsc(Long idxStore);
    Page<Product> findByIdxStoreAndIsActiveIsTrueOrderBySequenceAsc(Long idxStore, Pageable pageable);
    Page<Product> findByProductCategory_IdxAndIsActiveIsTrueOrderBySequenceAsc(Long idxProductCategory, Pageable pageable);

    Product findByIdxAndIsActiveIsTrue(Long idx);
    long countByIdxStoreAndIsActiveIsTrue(Long sIdx);
    long countByProductCategory_IdxAndIsActiveIsTrue(Long cIdx);
}

interface ProductCustomRepository {

}

@Transactional(readOnly = true)
class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    public ProductCustomRepositoryImpl() {
        super(Product.class);
    }

}
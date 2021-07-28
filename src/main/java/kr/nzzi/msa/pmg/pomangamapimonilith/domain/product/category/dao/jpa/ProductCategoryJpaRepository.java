package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.category.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.category.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, Long>, ProductCategoryCustomRepository {

}

interface ProductCategoryCustomRepository {

}

@Transactional(readOnly = true)
class ProductCategoryCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCategoryCustomRepository {

    public ProductCategoryCustomRepositoryImpl() {
        super(ProductCategory.class);
    }


}

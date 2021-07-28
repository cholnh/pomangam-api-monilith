package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.model.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ProductSubCategoryJpaRepository extends JpaRepository<ProductSubCategory, Long>, ProductSubCategoryCustomRepository {
    List<ProductSubCategory> findByIdxAndIsActiveIsTrue(Long cIdx);
}

interface ProductSubCategoryCustomRepository {

}

@Transactional(readOnly = true)
class ProductSubCategoryCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductSubCategoryCustomRepository {

    public ProductSubCategoryCustomRepositoryImpl() {
        super(ProductSubCategory.class);
    }


}

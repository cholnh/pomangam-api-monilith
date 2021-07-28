package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.log.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.log.model.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface OrderLogJpaRepository extends JpaRepository<OrderLog, Long>, OrderLogCustomRepository {

}

interface OrderLogCustomRepository {

}

@Transactional(readOnly = true)
class OrderLogCustomRepositoryImpl extends QuerydslRepositorySupport implements OrderLogCustomRepository {

    public OrderLogCustomRepositoryImpl() {
        super(OrderLog.class);
    }


}
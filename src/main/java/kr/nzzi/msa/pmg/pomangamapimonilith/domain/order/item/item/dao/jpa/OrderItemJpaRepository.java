package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long>, OrderItemCustomRepository {

}

interface OrderItemCustomRepository {

}

@Transactional(readOnly = true)
class OrderItemCustomRepositoryImpl extends QuerydslRepositorySupport implements OrderItemCustomRepository {

    public OrderItemCustomRepositoryImpl() {
        super(OrderItem.class);
    }


}
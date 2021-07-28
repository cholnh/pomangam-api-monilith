package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.model.OrderTime;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * orderTime - store [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "order_time_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderTimeMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_order_time", nullable = false)
    private OrderTime orderTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_store", nullable = false)
    private Store store;

    @Builder
    public OrderTimeMapper(OrderTime orderTime, Store store) {
        this.orderTime = orderTime;
        this.store = store;
    }
}

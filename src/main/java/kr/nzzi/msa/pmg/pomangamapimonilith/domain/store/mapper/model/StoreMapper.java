package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * deliverySite - store [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "store_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreMapper extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_delivery_site", nullable = false)
    private DeliverySite deliverySite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_store", nullable = false)
    private Store store;

    @Builder
    public StoreMapper(DeliverySite deliverySite, Store store) {
        this.deliverySite = deliverySite;
        this.store = store;
    }
}

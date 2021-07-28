package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.mapper.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.model.Event;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * event - deliverySite [N:M] 연관관계 설정을 위한 양방향 Mapper
 */
@Entity
@Table(name = "event_mapper_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
public class EventMapper extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_event", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx_delivery_site", nullable = false)
    private DeliverySite deliverySite;

    @Builder
    public EventMapper(Event event, DeliverySite deliverySite) {
        this.event = event;
        this.deliverySite = deliverySite;
    }
}

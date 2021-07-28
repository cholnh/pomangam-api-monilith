package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.region.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.model.DeliverySite;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "deliverySites")
public class Region extends Auditable {

    /**
     * 지역명
     * 글자수: utf8 기준 / 영문 20자 / 한글 20자
     */
    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<DeliverySite> deliverySites;

    @Builder
    public Region(Long idx, String name, List<DeliverySite> deliverySites) {
        super.setIdx(idx);
        this.name = name;
        if(deliverySites != null) {
            this.deliverySites = deliverySites;
        }
    }
}

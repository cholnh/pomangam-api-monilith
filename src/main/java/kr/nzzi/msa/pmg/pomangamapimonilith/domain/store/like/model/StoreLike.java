package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.like.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "store_like_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreLike extends Auditable {

    /**
     * 유저 인덱스
     */
    @Column(name="idx_user", nullable = false)
    private Long idxUser;

    /**
     * 업체 인덱스
     */
    @Column(name="idx_store", nullable = false)
    private Long idxStore;

    @Builder
    public StoreLike(Long idxUser, Long idxStore) {
        this.idxUser = idxUser;
        this.idxStore = idxStore;
    }
}


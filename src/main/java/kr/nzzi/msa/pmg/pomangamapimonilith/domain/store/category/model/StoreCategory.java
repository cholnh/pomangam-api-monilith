package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.category.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store_category_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "stores")
public class StoreCategory extends Auditable {

    /**
     * 음식점 카테고리 타이틀
     * ex) 한식, 중식, 양식 등
     * 글자수: utf8 기준 / 영문 20자 / 한글 20자
     */
    @Column(name = "category_title", nullable = false, length = 20)
    private String categoryTitle;

    @OneToMany(mappedBy = "storeCategory", fetch = FetchType.LAZY)
    @OrderBy("sequence ASC")
    private List<Store> stores = new ArrayList<>();

    @Builder
    public StoreCategory(Long idx, String categoryTitle, List<Store> stores) {
        super.setIdx(idx);
        this.categoryTitle = categoryTitle;
        if(stores != null) {
            this.stores = stores;
        }
    }
}

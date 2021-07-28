package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.sub.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.ProductSub;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "order_item_sub_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderItemSub extends Auditable {

    /**
     * 서브 제품
     * 단방향 매핑
     */
    @JoinColumn(name = "idx_product_sub")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProductSub productSub;

    /**
     * 주문 수량
     */
    @Column(name = "quantity", nullable = false)
    private Short quantity;

    @Builder
    public OrderItemSub(ProductSub productSub, Short quantity) {
        this.productSub = productSub;
        this.quantity = quantity;
    }
}


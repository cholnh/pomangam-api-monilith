package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.sub.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.sub.model.OrderItemSub;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.model.ProductSub;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderItemSubRequestDto implements Serializable {

    private Long idxProductSub;
    private Short quantity;

    public OrderItemSub toEntity() {
        OrderItemSub entity = OrderItemSub.builder()
                .productSub(ProductSub.builder().idx(this.idxProductSub).build())
                .quantity(this.quantity)
                .build();
        return entity;
    }
}

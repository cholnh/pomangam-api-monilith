package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.image.model.ProductImage;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductSummaryDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;

    private int salePrice;
    private String name;
    private String productImageMainPath;

    private ProductType productType;

    private Boolean isTempActive;

    public static ProductSummaryDto fromEntity(Product entity) {
        if(entity == null) return null;
        ProductSummaryDto dto = new ModelMapper().map(entity, ProductSummaryDto.class);

        // images
        List<ProductImage> productImages = entity.getImages();
        if(productImages != null && !productImages.isEmpty()) {
            for(ProductImage productImage : productImages) {
                switch (productImage.getImageType()) {
                    case MAIN:
                        dto.setProductImageMainPath(productImage.getImagePath()+"?v="+productImage.getModifyDate());
                        break;
                    default:
                        break;
                }
            }
        }

        // name
        dto.setName(entity.getProductInfo().getName());

        // cost
        dto.setSalePrice(entity.getCost().saleCost());

        dto.setProductType(entity.getProductType());

        return dto;
    }

    public static List<ProductSummaryDto> fromEntities(List<Product> entities) {
        if(entities == null) return null;
        List<ProductSummaryDto> dtos = new ArrayList<>();
        for(Product entity : entities) {
            ProductSummaryDto dto = fromEntity(entity);
            if(dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }
}

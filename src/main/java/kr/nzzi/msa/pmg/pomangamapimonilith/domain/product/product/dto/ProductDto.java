package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.image.model.ProductImage;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.Product;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.ProductType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.model.info.ProductInfo;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.dto.ProductReplyDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.category.dto.ProductSubCategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private Long idxStore;
    private Integer salePrice;
    private ProductInfo productInfo;
    private String productCategoryTitle;
    private Integer cntLike;
    private Integer cntReply;
    private Integer sequence;

    // images
    private String productImageMainPath;
    private List<String> productImageSubPaths = new ArrayList<>();

    // like
    private Boolean isLike;

    // reply
    private List<ProductReplyDto> replies;

    // product category
    private List<ProductSubCategoryDto> productSubCategories;

    private ProductType productType;

    private Boolean isTempActive;

    public Product toEntity() {
        Product entity = new ModelMapper().map(this, Product.class);
        return entity;
    }
    
    public static ProductDto fromEntity(Product entity) {
        if(entity == null) return null;
        ProductDto dto = new ModelMapper().map(entity, ProductDto.class);

        // images
        List<ProductImage> productImages = entity.getImages();
        if(productImages != null && !productImages.isEmpty()) {
            for(ProductImage productImage : productImages) {
                switch (productImage.getImageType()) {
                    case MAIN:
                        dto.setProductImageMainPath(productImage.getImagePath()+"?v="+productImage.getModifyDate());
                        break;
                    case SUB:
                        dto.getProductImageSubPaths().add(productImage.getImagePath()+"?v="+productImage.getModifyDate());
                        break;
                }
            }
        }

        // category
        dto.setProductCategoryTitle(entity.getProductCategory().getCategoryTitle());

        // cost
        dto.setSalePrice(entity.getCost().saleCost());

        return dto;
    }
    
    public static List<ProductDto> fromEntities(List<Product> entities) {
        if(entities == null) return null;
        List<ProductDto> dtos = new ArrayList<>();
        for(Product entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}

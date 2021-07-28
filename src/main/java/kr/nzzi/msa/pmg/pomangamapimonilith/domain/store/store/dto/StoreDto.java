package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.category.dto.ProductCategoryDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.image.model.StoreImage;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.info.ProductionInfo;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.info.StoreInfo;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.schedule.StoreSchedule;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.story.story.dto.StoreStoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StoreDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;

    private String storeCategory;
    private StoreInfo storeInfo;
    private ProductionInfo productionInfo;
    private StoreSchedule storeSchedule;
    private Float avgStar;
    private Integer cntLike;
    private Integer cntReview;
    private Integer cntOrder;
    private Integer sequence;

    // images
    private String brandImagePath;
    private String storeImageMainPath;
    private List<String> storeImageSubPaths = new ArrayList<>();

    // product category
    private List<ProductCategoryDto> productCategories;

    // story
    private List<StoreStoryDto> stories;

    // like
    private Boolean isLike;

    private Integer quantityOrderable;  // 주문 가능 수량

    public Store toEntity() {
        Store entity = new ModelMapper().map(this, Store.class);
        return entity;
    }

    public static StoreDto fromEntity(Store entity) {
        if(entity == null) return null;
        StoreDto dto = new ModelMapper().map(entity, StoreDto.class);

        // images
        List<StoreImage> storeImages = entity.getImages();
        if(storeImages != null && !storeImages.isEmpty()) {
            for(StoreImage storeImage : storeImages) {
                switch (storeImage.getImageType()) {
                    case MAIN:
                        dto.setStoreImageMainPath(storeImage.getImagePath()+"?v="+storeImage.getModifyDate());
                        break;
                    case SUB:
                        dto.getStoreImageSubPaths().add(storeImage.getImagePath()+"?v="+storeImage.getModifyDate());
                        break;
                    case BRAND:
                        dto.setBrandImagePath(storeImage.getImagePath()+"?v="+storeImage.getModifyDate());
                        break;
                }
            }
        }

        // category
        dto.setStoreCategory(entity.getStoreCategory().getCategoryTitle());

        // product category
        dto.setProductCategories(ProductCategoryDto.fromEntities(entity.getProductCategories()));

        // story
        dto.setStories(StoreStoryDto.fromEntities(entity.getStories()));

        return dto;
    }

    public static List<StoreDto> fromEntities(List<Store> entities) {
        if(entities == null) return null;
        List<StoreDto> dtos = new ArrayList<>();
        for(Store entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}

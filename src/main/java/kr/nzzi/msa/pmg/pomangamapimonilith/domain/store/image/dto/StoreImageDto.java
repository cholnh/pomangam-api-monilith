package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.image.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.image.model.StoreImage;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.image.model.StoreImageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StoreImageDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private String imagePath;
    private StoreImageType imageType;

    public StoreImage toEntity() {
        StoreImage entity = new ModelMapper().map(this, StoreImage.class);
        return entity;
    }

    public static StoreImageDto fromEntity(StoreImage entity) {
        if(entity == null) return null;
        StoreImageDto dto = new ModelMapper().map(entity, StoreImageDto.class);
        return dto;
    }

    public static List<StoreImageDto> fromEntities(List<StoreImage> entities) {
        if(entities == null) return null;
        List<StoreImageDto> dtos = new ArrayList<>();
        for(StoreImage entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.model.OrderTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderTimeDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;

    private LocalTime arrivalTime;
    private LocalTime pickUpTime;
    private LocalTime orderEndTime;

    public OrderTime toEntity() {
        OrderTime entity = new ModelMapper().map(this, OrderTime.class);
        return entity;
    }

    public static OrderTimeDto fromEntity(OrderTime entity) {
        if(entity == null) return null;
        OrderTimeDto dto = new ModelMapper().map(entity, OrderTimeDto.class);
        return dto;
    }

    public static List<OrderTimeDto> fromEntities(List<OrderTime> entities) {
        if(entities == null) return null;
        List<OrderTimeDto> dtos = new ArrayList<>();
        for(OrderTime entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}
package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.dao.jpa.OrderTimeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.dto.OrderTimeDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.service.OrderTimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderTimeServiceImpl implements OrderTimeService {

    private OrderTimeJpaRepository orderTimeRepo;

    public List<OrderTimeDto> findByIdxDeliverySite(Long dIdx) {
        return OrderTimeDto.fromEntities(orderTimeRepo.findByIdxDeliverySite(dIdx));
    }

    public List<OrderTimeDto> findByIdxStore(Long sIdx) {
        return OrderTimeDto.fromEntities(orderTimeRepo.findByIdxStore(sIdx));
    }
}

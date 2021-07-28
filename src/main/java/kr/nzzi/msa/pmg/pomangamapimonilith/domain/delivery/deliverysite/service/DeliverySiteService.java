package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.deliverysite.dto.DeliverySiteDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliverySiteService {
    List<DeliverySiteDto> findAll(Pageable pageable);
    DeliverySiteDto findByIdx(Long dIdx);
    long count();
    List<DeliverySiteDto> search(String query);
}

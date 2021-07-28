package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.ordertime.ordertime.service.impl.OrderTimeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderTimeApi {

    OrderTimeServiceImpl orderTimeService;

    @GetMapping("/dsites/{dIdx}/ordertimes")
    public ResponseEntity<?> findByIdxDeliverySite(
            @PathVariable(value = "dIdx", required = true) Long dIdx
    ) {
        return new ResponseEntity(orderTimeService.findByIdxDeliverySite(dIdx), HttpStatus.OK);
    }
}
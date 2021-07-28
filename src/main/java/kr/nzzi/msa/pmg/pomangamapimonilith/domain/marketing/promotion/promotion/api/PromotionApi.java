package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.promotion.promotion.service.impl.PromotionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dsites/{dIdx}/promotions")
@AllArgsConstructor
public class PromotionApi {

    PromotionServiceImpl promotionService;

    @GetMapping
    public ResponseEntity<?> findByIdxDeliverySite(
            @PathVariable(value = "dIdx", required = true) Long dIdx
    ) {

        return new ResponseEntity(promotionService.findByIdxDeliverySite(dIdx), HttpStatus.OK);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findByIdx(@PathVariable(value = "idx", required = true) Long idx
    ) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity(HttpStatus.OK);
    }

}

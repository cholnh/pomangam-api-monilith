package kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.delivery.detailsite.service.impl.DeliveryDetailSiteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dsites/{dIdx}/details")
@AllArgsConstructor
public class DeliveryDetailSiteApi {

    DeliveryDetailSiteServiceImpl detailSiteService;

    @GetMapping
    public ResponseEntity<?> findByIdxDeliverySite(
            @PathVariable(value = "dIdx", required = true) Long dIdx
    ) {
        return new ResponseEntity(detailSiteService.findByIdxDeliverySite(dIdx), HttpStatus.OK);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findByIdx(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "idx", required = true) Long idx
    ) {
        return new ResponseEntity(detailSiteService.findByIdx(idx), HttpStatus.OK);
    }

    @GetMapping("/search/count")
    public ResponseEntity<?> count(
            @PathVariable(value = "dIdx", required = true) Long dIdx
    ) {
        return new ResponseEntity(detailSiteService.count(), HttpStatus.OK);
    }

}

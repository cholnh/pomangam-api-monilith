package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.sub.sub.service.impl.ProductSubServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dsites/{dIdx}/stores/{sIdx}/products/{pIdx}/subs")
@AllArgsConstructor
public class ProductSubApi {

    ProductSubServiceImpl productSubService;

    @GetMapping
    public ResponseEntity<?> findByIdxProduct(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @RequestParam(value = "cIdx", required = false) Long cIdx
    ) {
        if(cIdx == null) {
            return new ResponseEntity(productSubService.findByIdxProduct(pIdx), HttpStatus.OK);
        } else {
            return new ResponseEntity(productSubService.findByIdxProductSubCategory(cIdx), HttpStatus.OK);
        }

    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findByIdx(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @PathVariable(value = "idx", required = true) Long idx
    ) {
        return new ResponseEntity(productSubService.findByIdx(idx), HttpStatus.OK);
    }

    @GetMapping("/search/count")
    public ResponseEntity<?> count(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx
    ) {
        return new ResponseEntity(productSubService.count(), HttpStatus.OK);
    }
}

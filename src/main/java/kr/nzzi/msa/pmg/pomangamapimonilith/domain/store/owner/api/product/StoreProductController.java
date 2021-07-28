package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.api.product;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.product.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StoreProductController {

    ProductServiceImpl productService;

    @PreAuthorize("isAuthenticated() and (hasAnyRole('ROLE_STORE_OWNER', 'ROLE_ADMIN', 'ROLE_STAFF'))")
    @GetMapping("/store/{sIdx}/products")
    public ResponseEntity<?> findByIdxStore(
            @PathVariable(value = "sIdx", required = true) Long sIdx
    ) {
        return new ResponseEntity(productService.findByIdxStore(sIdx), HttpStatus.OK);
    }
}

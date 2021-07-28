package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.api.store;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.service.impl.StoreAuthenticationServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.service.impl.OwnerStoreServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dto.StoreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class OwnerStoreController {

    StoreAuthenticationServiceImpl authenticationService;
    OwnerStoreServiceImpl storeService;

    @PreAuthorize("isAuthenticated() and (hasAnyRole('ROLE_STORE_OWNER', 'ROLE_ADMIN', 'ROLE_STAFF'))")
    @GetMapping("/store/{sIdx}")
    public ResponseEntity<?> findByIdx(
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            Authentication auth
    ) {
        authenticationService.authenticate(auth, sIdx);
        return new ResponseEntity(storeService.findByIdx(sIdx), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (hasAnyRole('ROLE_STORE_OWNER', 'ROLE_ADMIN', 'ROLE_STAFF'))")
    @PatchMapping("/store/{sIdx}")
    public ResponseEntity<?> patch(
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @RequestBody(required = true) StoreDto dto,
            Authentication auth
    ) {
        authenticationService.authenticate(auth, sIdx);
        dto.setIdx(sIdx);
        return new ResponseEntity(storeService.patch(dto), HttpStatus.OK);
    }

}

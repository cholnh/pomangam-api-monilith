package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.service.impl.StoreReviewLikeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/dsites/{dIdx}/stores/{sIdx}/reviews/{rIdx}/likes")
@AllArgsConstructor
public class StoreReviewLikeApi {

    StoreReviewLikeServiceImpl storeReviewLikeService;

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/like")
    public ResponseEntity<?> like(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "rIdx", required = true) Long rIdx,
            Principal principal
    ) {
        storeReviewLikeService.like(principal.getName(), rIdx);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/unlike")
    public ResponseEntity<?> unlike(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "rIdx", required = true) Long rIdx,
            Principal principal
    ) {
        storeReviewLikeService.cancelLike(principal.getName(), rIdx);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/toggle")
    public ResponseEntity<Boolean> toggle(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "rIdx", required = true) Long rIdx,
            Principal principal
    ) {
        return new ResponseEntity(storeReviewLikeService.toggle(principal.getName(), rIdx), HttpStatus.OK);
    }
}

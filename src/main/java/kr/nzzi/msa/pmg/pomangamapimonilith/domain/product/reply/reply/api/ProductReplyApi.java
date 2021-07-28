package kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.api;

import com.fasterxml.jackson.annotation.JsonView;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.dto.ProductReplyDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.dto.ProductReplyDtoView;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.product.reply.reply.service.impl.ProductReplyServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/dsites/{dIdx}/stores/{sIdx}/products/{pIdx}/replies")
@AllArgsConstructor
public class ProductReplyApi {

    ProductReplyServiceImpl productReplyService;
    UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> findByIdxProduct(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @PageableDefault(sort = {"idx"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        return new ResponseEntity(productReplyService.findByIdxProduct(pIdx, uIdx, pageable), HttpStatus.OK);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findByIdx(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @PathVariable(value = "idx", required = true) Long idx,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        return new ResponseEntity(productReplyService.findByIdx(idx, uIdx), HttpStatus.OK);
    }

    @GetMapping("/search/count")
    public ResponseEntity<?> count(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx
    ) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @PostMapping
    @JsonView(ProductReplyDtoView.CustomView.class)
    public ResponseEntity<?> post(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            ProductReplyDto dto,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        dto.setIdxUser(uIdx);       // 댓글작성자 등록 (who)
        dto.setIdxProduct(pIdx);    // 리뷰 인덱스 등록 (where)
        return new ResponseEntity(productReplyService.save(dto), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/{idx}")
    @JsonView(ProductReplyDtoView.CustomView.class)
    public ResponseEntity<?> patch(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @PathVariable(value = "idx", required = true) Long idx,
            ProductReplyDto dto,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        if(productReplyService.findByIdx(idx, uIdx).getIsOwn()) {
            dto.setIdx(idx);
            dto.setIdxUser(uIdx);
            dto.setIdxProduct(pIdx);
            return new ResponseEntity(productReplyService.update(dto), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(
            @PathVariable(value = "dIdx", required = true) Long dIdx,
            @PathVariable(value = "sIdx", required = true) Long sIdx,
            @PathVariable(value = "pIdx", required = true) Long pIdx,
            @PathVariable(value = "idx", required = true) Long idx,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        if(productReplyService.findByIdx(idx, uIdx).getIsOwn()) {
            productReplyService.delete(pIdx, idx);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}

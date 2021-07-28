package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.service.impl.PointLogServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users/{phn}/points")
@AllArgsConstructor
public class PointLogApi {

    UserServiceImpl userService;
    PointLogServiceImpl pointLogService;

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @GetMapping
    public ResponseEntity<?> findByIdxUser(
            @PathVariable(value = "phn") String phoneNumber,
            @PageableDefault(sort = {"idx"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Principal principal
    ) {
        Long uIdx = userService.findIdxByPhoneNumber(principal.getName());
        return new ResponseEntity(pointLogService.findByIdxUser(uIdx, pageable), HttpStatus.OK);
    }
}

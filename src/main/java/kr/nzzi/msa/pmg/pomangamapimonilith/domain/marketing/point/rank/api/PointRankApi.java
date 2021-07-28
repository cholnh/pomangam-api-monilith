package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.rank.service.impl.PointRankServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointRankApi {

    PointRankServiceImpl pointRankService;

    @GetMapping("/rank")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity(pointRankService.findAll(), HttpStatus.OK);
    }
}

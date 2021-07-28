package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.api;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.model.CommonMap;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.cmap.service.impl.CommonMapServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/maps")
@AllArgsConstructor
public class CommonMapApi {

    CommonMapServiceImpl commonMapService;

    @GetMapping("/{key}")
    public ResponseEntity<?> findByKey(
            @PathVariable(value = "key", required = true) String key
    ) {
        List<CommonMap> dto = commonMapService.findAllByKey(key);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }
}

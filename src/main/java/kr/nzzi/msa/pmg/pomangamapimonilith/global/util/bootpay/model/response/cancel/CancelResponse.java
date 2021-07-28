package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.cancel;

import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.ResDefault;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CancelResponse extends ResDefault {
    CancelData data;
}

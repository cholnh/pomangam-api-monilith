package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.verify;

import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.ResDefault;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VerifyResponse extends ResDefault {
    VerifyData data;
}

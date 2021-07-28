package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateInputDto extends PhoneNumber {
    private String password;
}

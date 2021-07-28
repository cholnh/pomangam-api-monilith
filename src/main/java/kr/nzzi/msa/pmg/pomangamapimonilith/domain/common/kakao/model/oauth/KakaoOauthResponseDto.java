package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.model.oauth;

import lombok.Data;

import java.io.Serializable;

@Data
public class KakaoOauthResponseDto implements Serializable {
    Integer id;
    String connected_at;
    Properties properties;
    KakaoAccount kakao_account;
}
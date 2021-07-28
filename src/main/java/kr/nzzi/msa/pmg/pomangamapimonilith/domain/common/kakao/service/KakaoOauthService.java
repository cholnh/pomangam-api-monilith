package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.service;

public interface KakaoOauthService {
    boolean verifyOauthLogin(String phoneNumber, String token);
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.dao.jpa.KakaoAuthRepositoryImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.service.KakaoAuthService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bizm.template.AuthTemplate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class KakaoAuthServiceImpl implements KakaoAuthService {

    KakaoAuthRepositoryImpl kakaoAuthRepository;
    UserJpaRepository userRepo;

    private final static int authCodeLength = 4;

    public static int getAuthCodeLength() {
        return authCodeLength;
    }

    @Override
    public String getAuthCode() {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < authCodeLength; i++) {
            builder.append(secureRandom.nextInt(10));
        }
        return builder.toString();
    }

    public boolean checkAbusing(String ip) {
        return kakaoAuthRepository.checkAbusing(ip);
    }

    @Override
    public ResponseEntity<?> sendAuthCode(String phone_number, String auth_code) {
        Map<String, String> data = new HashMap<>();
        data.put("auth_code", auth_code);
        return AuthTemplate.send(phone_number, data);
    }

    @Override
    public void saveAuthCode(String phone_number, String auth_code) {
        kakaoAuthRepository.saveAuthCode(phone_number, auth_code);
    }

    @Override
    public boolean checkAuthCode(String phone_number, String auth_code) {
        boolean isValid = kakaoAuthRepository.checkAuthCode(phone_number, auth_code);
        if(isValid) {
            resetFailCount(phone_number);
        }
        return isValid;
    }

    public void resetFailCount(String phoneNumber) {
        User user = userRepo.findByPhoneNumberAndIsActiveIsTrue(phoneNumber);
        if(user != null) {
            user.getPassword().setFailedCount(0);
            userRepo.save(user);
        }
    }

    @Override
    public boolean checkAuthCodeNotDelete(String phone_number, String auth_code) {
        boolean isValid = kakaoAuthRepository.checkAuthCodeNotDelete(phone_number, auth_code);
        if(isValid) {
            resetFailCount(phone_number);
        }
        return isValid;
    }

    public void deleteAuthCode(String phone_number) {
        kakaoAuthRepository.deleteAuthCode(phone_number);
    }
}

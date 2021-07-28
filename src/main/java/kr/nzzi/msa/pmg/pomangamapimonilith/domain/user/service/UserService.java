package kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.model.PointType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dto.UserDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto findByPhoneNumber(String phoneNumber);

    Long findIdxByPhoneNumber(String phoneNumber);

    List<UserDto> findAll();

    List<UserDto> findAll(Pageable pageable);

    UserDto saveUser(User user);

    UserDto updateUserPassword(String phoneNumber, String password);

    Boolean isExistByPhone(String phoneNumber);

    Boolean isExistByNickname(String nickname);

    UserDto patchUser(String phoneNumber, User user);

    Boolean deleteUser(String phoneNumber);

    int getPointByIdx(Long idx);

    int plusPointByIdx(Long idx, int point, PointType pointType, Long oIdx);

    int minusPointByIdx(Long idx, int point, PointType pointType, Long oIdx);
}

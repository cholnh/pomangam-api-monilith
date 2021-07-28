package kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.RandomNickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RandomNicknameJpaRepository extends JpaRepository<RandomNickname, Long> {
    @Query(value = "SELECT u.first FROM random_nickname_tbl u WHERE u.is_active = 'Y' ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findFirstByRandomAndIsActiveIsTrue();

    @Query(value = "SELECT u.second FROM random_nickname_tbl u WHERE u.is_active = 'Y' ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findSecondByRandomAndIsActiveIsTrue();
}


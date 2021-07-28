package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dao.jpa.staff;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.staff.FcmStaffToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


@RepositoryRestResource(exported = false)
public interface FcmStaffTokenJpaRepository extends JpaRepository<FcmStaffToken, Long>, FcmStaffTokenCustomRepository {
    FcmStaffToken findByIdxAndIsActiveIsTrue(Long idx);
    FcmStaffToken findByToken(String token);
}

interface FcmStaffTokenCustomRepository {
   
}

@Transactional(readOnly = true)
class FcmStaffTokenCustomRepositoryImpl extends QuerydslRepositorySupport implements FcmStaffTokenCustomRepository {

    public FcmStaffTokenCustomRepositoryImpl() {
        super(FcmStaffToken.class);
    }
}

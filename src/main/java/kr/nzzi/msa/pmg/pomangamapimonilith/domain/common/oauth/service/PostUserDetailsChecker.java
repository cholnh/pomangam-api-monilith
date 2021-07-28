package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.oauth.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.kakao.service.impl.KakaoAuthServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.dao.jpa.OwnerJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.model.Owner;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.staff.dao.jpa.StaffJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.staff.model.Staff;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Service;

@Service
public class PostUserDetailsChecker implements UserDetailsChecker {

    private UserJpaRepository userRepo;
    private OwnerJpaRepository storeOwnerJpaRepo;
    private StaffJpaRepository staffJpaRepo;
    private KakaoAuthServiceImpl kakaoAuthService;

    @Autowired
    public PostUserDetailsChecker(
        @Lazy UserJpaRepository userRepo,
        @Lazy OwnerJpaRepository storeOwnerJpaRepo,
        @Lazy StaffJpaRepository staffJpaRepo,
        @Lazy KakaoAuthServiceImpl kakaoAuthService
    ) {
        this.userRepo = userRepo;
        this.storeOwnerJpaRepo = storeOwnerJpaRepo;
        this.staffJpaRepo = staffJpaRepo;
        this.kakaoAuthService = kakaoAuthService;
    }

    @Override
    public void check(UserDetails toCheck) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String clientName = authentication.getName();
            switch (clientName) {
                case "guest":
                case "login": checkUser(toCheck); break;
                case "store_owner": checkStoreOwner(toCheck); break;
                case "staff": checkStaff(toCheck); break;
                default: throw new InternalAuthenticationServiceException("ANONYMOUS_AUTHENTICATION_CLIENT");
            }
        }
    }

    void checkUser(UserDetails toCheck) {
        User user = userRepo.findByPhoneNumberAndIsActiveIsTrue(toCheck.getUsername());
        if(user != null) {
            user.getPassword().setFailedCount(0);
            userRepo.save(user);
            kakaoAuthService.deleteAuthCode(user.getPhoneNumber());
        }
    }

    void checkStoreOwner(UserDetails toCheck) {
        Owner storeOwner = storeOwnerJpaRepo.findByIdAndIsActiveIsTrue(toCheck.getUsername());
        if(storeOwner != null) {
            storeOwner.getPassword().setFailedCount(0);
            storeOwnerJpaRepo.save(storeOwner);
        }
    }

    void checkStaff(UserDetails toCheck) {
        Staff staff = staffJpaRepo.findByIdAndIsActiveIsTrue(toCheck.getUsername());
        if(staff != null) {
            staff.getPassword().setFailedCount(0);
            staffJpaRepo.save(staff);
        }
    }
}

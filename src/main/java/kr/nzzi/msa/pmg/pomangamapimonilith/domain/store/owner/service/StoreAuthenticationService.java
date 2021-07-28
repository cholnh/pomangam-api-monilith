package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.service;

import org.springframework.security.core.Authentication;

public interface StoreAuthenticationService {
    boolean isStoreOwner(Authentication auth, Long sIdx);
    Long authenticate(Authentication auth, Long sIdx);
}

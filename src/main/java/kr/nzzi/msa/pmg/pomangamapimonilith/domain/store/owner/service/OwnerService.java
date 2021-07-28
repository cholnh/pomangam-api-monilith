package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.dto.OwnerDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.model.Owner;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerService {

    OwnerDto findById(String id);

    Long findIdxById(String id);

    List<OwnerDto> findAll();

    List<OwnerDto> findAll(Pageable pageable);

    OwnerDto saveOwner(Owner owner);

    OwnerDto updateOwnerPassword(String id, String password);

    Boolean isExistById(String id);

    OwnerDto patchOwner(String id, Owner owner);

    Boolean deleteOwner(String id);
}

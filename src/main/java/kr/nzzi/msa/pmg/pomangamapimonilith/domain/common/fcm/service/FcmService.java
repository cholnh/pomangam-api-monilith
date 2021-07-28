package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.FcmRequestDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.client.FcmClientTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.owner.FcmOwnerTokenDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.dto.staff.FcmStaffTokenDto;

public interface FcmService {
    String send(FcmRequestDto fcmRequest);
    FcmClientTokenDto postClient(FcmClientTokenDto token);
    FcmOwnerTokenDto postOwner(FcmOwnerTokenDto token);
    FcmStaffTokenDto postStaff(FcmStaffTokenDto token);
    void deleteClient(Long fIdx);
    void deleteOwner(Long fIdx);
    void deleteStaff(Long fIdx);

}

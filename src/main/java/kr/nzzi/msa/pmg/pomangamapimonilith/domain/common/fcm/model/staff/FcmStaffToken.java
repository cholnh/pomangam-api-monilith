package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.staff;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.FcmToken;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fcm_staff_token_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmStaffToken extends FcmToken {

    @Column(name = "id_staff")
    private String idStaff;

    @Builder
    public FcmStaffToken(Long idx, String token, String idStaff) {
        super.setIdx(idx);
        super.setToken(token);
        this.idStaff = idStaff;
    }
}

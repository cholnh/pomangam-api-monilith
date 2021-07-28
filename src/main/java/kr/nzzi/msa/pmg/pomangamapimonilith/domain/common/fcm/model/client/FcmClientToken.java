package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.client;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.fcm.model.FcmToken;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fcm_client_token_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmClientToken extends FcmToken {

    /**
     * User Id
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    public FcmClientToken(Long idx, String token, String phoneNumber) {
        super.setIdx(idx);
        super.setToken(token);
        this.phoneNumber = phoneNumber;
    }
}

package kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "random_nickname_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RandomNickname extends Auditable {

    /**
     * 첫번째 글자
     * 글자수: utf8 기준 / 영문 10자 / 한글 10자
     */
    @Column(name = "first", nullable = false, length = 10)
    private String first;

    /**
     * 두번째 글자
     * 글자수: utf8 기준 / 영문 10자 / 한글 10자
     */
    @Column(name = "second", nullable = false, length = 10)
    private String second;

    @Builder
    public RandomNickname(String first, String second) {
        this.first = first;
        this.second = second;
    }
}

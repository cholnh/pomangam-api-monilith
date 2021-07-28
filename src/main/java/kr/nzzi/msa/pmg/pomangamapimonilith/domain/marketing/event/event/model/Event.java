package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.event.event.model;

import kr.nzzi.msa.pmg.pomangamapimonilith.global._base.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_tbl")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Event extends Auditable {

    /**
     * 이벤트 제목
     * 글자수: utf8 기준 / 영문 100자 / 한글 100자
     */
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    /**
     * 이벤트 내용 (webView html)
     * TEXT: 65535 Byte (64KB) / utf8 기준(3바이트 문자)으로 21844 글자 저장가능
     */
    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String contents;

    /**
     * 이벤트 적용 시작 기간
     */
    @Column(name = "begin_date", nullable = false)
    private LocalDateTime beginDate;

    /**
     * 이벤트 적용 종료 기간
     * null: 기한 무제한
     */
    @Column(name = "end_date", nullable = true)
    private LocalDateTime endDate;

    @Builder
    public Event(Long idx, String title, String contents, LocalDateTime beginDate, LocalDateTime endDate) {
        super.setIdx(idx);
        this.title = title;
        this.contents = contents;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public boolean isValid() {
        return super.getIsActive() &&
                LocalDateTime.now().isAfter(this.beginDate) &&
                (this.endDate == null || LocalDateTime.now().isBefore(this.endDate));
    }
}


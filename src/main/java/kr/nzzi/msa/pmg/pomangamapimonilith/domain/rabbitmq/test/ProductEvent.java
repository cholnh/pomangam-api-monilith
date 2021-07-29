package kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@ToString
public class ProductEvent {
    private String name;
    private int price;
}

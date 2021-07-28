package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.payment.vbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.payment.vbank.model.VBankDeposit;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.payment.vbank.model.VBankStatus;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.configuration.mapper.CustomMapper;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VBankDepositDto implements Serializable {

    private Long idx;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;
    private Integer input;
    private String bank;
    private Integer remain;
    private String name;
    private String transferDate;
    private String content;
    private VBankStatus status;
    private Long idxOrder;

    public VBankDeposit toEntity() {
        return CustomMapper.getInstance()
                .map(this, VBankDeposit.class);
    }

    public static VBankDepositDto from(VBankDeposit entity) {
        if(entity == null)
            return null;
        return CustomMapper.getInstance().map(entity, VBankDepositDto.class);
    }

    public static List<VBankDepositDto> from(List<VBankDeposit> entities) {
        if(entities == null)
            return null;
        return entities.stream().map(VBankDepositDto::from).collect(Collectors.toList());
    }

    public boolean equals(VBankDepositDto another) {
        return this.transferDate.equals(another.getTransferDate()) &&
                this.name.equals(another.getName()) &&
                this.input.intValue() == another.getInput().intValue() &&
                this.remain.intValue() == another.getRemain().intValue();
    }
}

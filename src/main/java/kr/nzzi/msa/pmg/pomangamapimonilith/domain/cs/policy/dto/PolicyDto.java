package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.policy.dto;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.policy.model.Policy;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PolicyDto implements Serializable {

    private Long idx;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private String policyName;
    private String policyContents;

    public Policy toEntity() {
        Policy entity = new ModelMapper().map(this, Policy.class);
        return entity;
    }

    public static PolicyDto fromEntity(Policy entity) {
        if(entity == null) return null;
        PolicyDto dto = new ModelMapper().map(entity, PolicyDto.class);
        return dto;
    }

    public static List<PolicyDto> fromEntities(List<Policy> entities) {
        if(entities == null) return null;
        List<PolicyDto> dtos = new ArrayList<>();
        for(Policy entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}

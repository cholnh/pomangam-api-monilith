package kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.policy.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.cs.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PolicyJpaRepository extends JpaRepository<Policy, Long> {
    Policy findByPolicyNameAndIsActiveIsTrue(@Param("policyName") String policyName);
}

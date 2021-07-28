package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.payment.vbank.dao.jpa;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.payment.vbank.model.VBankRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
public interface VBankRefundJpaRepository extends JpaRepository<VBankRefund, Long>, VBankRefundCustomRepository {

}

interface VBankRefundCustomRepository {

}

@Transactional(readOnly = true)
class VBankRefundCustomRepositoryImpl extends QuerydslRepositorySupport implements VBankRefundCustomRepository {

    public VBankRefundCustomRepositoryImpl() {
        super(VBankRefund.class);
    }


}
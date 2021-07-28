package kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.order.model.payment_info;

public enum PaymentType {
    CONTACT_CREDIT_CARD,
    CONTACT_CASH,

    COMMON_CREDIT_CARD,
    COMMON_PHONE,
    COMMON_V_BANK,
    COMMON_BANK,
    COMMON_KAKAOPAY,
    COMMON_REMOTE_PAY,

    PERIODIC_CREDIT_CARD,
    PERIODIC_FIRM_BANK
}

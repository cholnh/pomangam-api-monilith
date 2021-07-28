package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.exception;

public class CouponException extends RuntimeException {
    public CouponException() {
        super();
    }
    public CouponException(String message) {
        super(message);
    }
    public CouponException(String message, Throwable cause) {
        super(message, cause);
    }
    public CouponException(Throwable cause) {
        super(cause);
    }
}

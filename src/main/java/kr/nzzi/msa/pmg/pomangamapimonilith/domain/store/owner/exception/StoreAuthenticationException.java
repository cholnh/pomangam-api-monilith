package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.owner.exception;

public class StoreAuthenticationException extends RuntimeException {
    public StoreAuthenticationException() {
        super();
    }
    public StoreAuthenticationException(String message) {
        super(message);
    }
    public StoreAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    public StoreAuthenticationException(Throwable cause) {
        super(cause);
    }
}

package fun.lww.securitycore.imagevalidatecode;

import org.springframework.security.core.AuthenticationException;

public class ImageValidateCodeException extends AuthenticationException {

    public ImageValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageValidateCodeException(String msg) {
        super(msg);
    }

}

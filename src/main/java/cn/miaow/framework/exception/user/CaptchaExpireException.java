package cn.miaow.framework.exception.user;

/**
 * 验证码失效异常类
 */
public class CaptchaExpireException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jCaptcha.expire", null);
    }
}

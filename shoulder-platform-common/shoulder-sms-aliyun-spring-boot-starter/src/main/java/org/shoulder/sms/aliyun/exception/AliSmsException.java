package org.shoulder.sms.aliyun.exception;

/**
 * 阿里云短信服务异常
 *
 * @author lym
 */
public class AliSmsException extends Exception {
    /**
     * Instantiates a new AliSmsException.
     *
     * @param message the message
     */
    public AliSmsException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new AliSmsException.
     *
     * @param client_exception
     * @param cause            the cause
     */
    public AliSmsException(String client_exception, final Throwable cause) {
        super(client_exception, cause);
    }
}

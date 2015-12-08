package com.fujitsu.base.exception;

/**
 * Created by Barrie on 15/12/5.
 */
public class WeChatException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -7126891826253217660L;

    public WeChatException() {
        super();
    }

    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(Throwable paramThrowable) {
        super(paramThrowable);
    }

    public WeChatException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }
}

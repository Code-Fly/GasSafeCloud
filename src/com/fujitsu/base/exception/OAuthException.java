package com.fujitsu.base.exception;

/**
 * Created by Barrie on 15/12/5.
 */
public class OAuthException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -701488732956669519L;

    public OAuthException() {
        super();
    }

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(Throwable paramThrowable) {
        super(paramThrowable);
    }

    public OAuthException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }

}

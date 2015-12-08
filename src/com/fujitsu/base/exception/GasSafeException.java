package com.fujitsu.base.exception;

/**
 * Created by Barrie on 15/12/8.
 */
public class GasSafeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7983089681325630908L;

    public GasSafeException() {
        super();
    }

    public GasSafeException(String message) {
        super(message);
    }

    public GasSafeException(Throwable paramThrowable) {
        super(paramThrowable);
    }

    public GasSafeException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }

}

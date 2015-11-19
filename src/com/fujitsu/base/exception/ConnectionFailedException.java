/**
 * 
 */
package com.fujitsu.base.exception;

/**
 * @author Barrie
 *
 */
public class ConnectionFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -954246547431584056L;

	public ConnectionFailedException() {
        super();
    }
	
	public ConnectionFailedException(String message) {
		super(message);
	}
	
	public ConnectionFailedException(Throwable paramThrowable) {
        super(paramThrowable);
    }

	public ConnectionFailedException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}

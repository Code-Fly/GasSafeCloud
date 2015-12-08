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
	private static final long serialVersionUID = -3533799359894155475L;

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

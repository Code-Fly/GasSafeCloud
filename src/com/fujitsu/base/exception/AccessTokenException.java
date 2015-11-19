/**
 * 
 */
package com.fujitsu.base.exception;

/**
 * @author Barrie
 *
 */
public class AccessTokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -771241341034699954L;

	public AccessTokenException() {
		super();
	}

	public AccessTokenException(String message) {
		super(message);
	}

	public AccessTokenException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	public AccessTokenException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}

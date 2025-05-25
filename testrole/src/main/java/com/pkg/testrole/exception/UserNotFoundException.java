package com.pkg.testrole.exception;

public class UserNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
        super(message);
    }

    // Optional: add constructors for more flexibility
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

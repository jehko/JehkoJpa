package com.jehko.jpa.user.exception;

public class ExistEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExistEmailException(String message) {
		super(message);
	}
}

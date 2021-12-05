package com.jehko.jpa.notice.exception;

public class AlreadyDeletedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlreadyDeletedException(String message) {
		super(message);
	}
}

package com.jehko.jpa.notice.exception;

public class DuplicateNoticeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateNoticeException(String message) {
		super(message);
	}
}

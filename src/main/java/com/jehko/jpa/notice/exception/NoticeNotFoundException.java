package com.jehko.jpa.notice.exception;

public class NoticeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2952259089185872187L;

	public NoticeNotFoundException(String message) {
		super(message);
	}
}

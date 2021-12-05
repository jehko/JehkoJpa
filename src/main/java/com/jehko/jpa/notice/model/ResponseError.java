package com.jehko.jpa.notice.model;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseError {

	private String field;
	private String message;

	public static ResponseError of(FieldError e) {
		return ResponseError.builder().field(e.getField()).message(e.getDefaultMessage()).build();
	}
}

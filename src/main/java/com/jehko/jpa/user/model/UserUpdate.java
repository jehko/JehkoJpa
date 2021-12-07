package com.jehko.jpa.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserUpdate {
	
	@Size(max = 20, message = "연락처는 최대 20자까지 입력 가능합니다.")
	@NotBlank(message = "연락처는 필수 항목입니다.")
	private String phone;
}

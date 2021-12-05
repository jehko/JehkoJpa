package com.jehko.jpa.user.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

	@NotBlank(message = "이메일 항목은 필수입니다.")
	private String email;

	@NotBlank(message = "비밀번호 항목은 필수입니다.")
	private String password;
}

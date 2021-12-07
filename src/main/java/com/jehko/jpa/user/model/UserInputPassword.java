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
public class UserInputPassword {

	@NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
	private String password;


	@Size(min = 4, max = 20, message = "신규 비밀번호는 4-20자 사이의 길이로 입력해주세요.")
	@NotBlank(message = "신규 비밀번호는 필수 항목입니다.")
	private String newPassword;
}

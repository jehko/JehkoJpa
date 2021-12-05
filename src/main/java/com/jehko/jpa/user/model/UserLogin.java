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

	@NotBlank(message = "�̸��� �׸��� �ʼ��Դϴ�.")
	private String email;

	@NotBlank(message = "��й�ȣ �׸��� �ʼ��Դϴ�.")
	private String password;
}

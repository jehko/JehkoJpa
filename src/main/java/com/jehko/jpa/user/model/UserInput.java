package com.jehko.jpa.user.model;

import javax.validation.constraints.Email;
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
public class UserInput {

	@Email(message = "�̸��� ���Ŀ� �°� �Է����ּ���.")
	@NotBlank(message = "�̸����� �ʼ� �׸��Դϴ�.")
	private String email;

	@NotBlank(message = "�̸��� �ʼ� �׸��Դϴ�.")
	private String userName;

	@Size(min = 4, message = "��й�ȣ�� ���� �̻� �Է��ؾ� �մϴ�.")
	@NotBlank(message = "��й�ȣ�� �ʼ� �׸��Դϴ�.")
	private String password;

	@Size(max = 20, message = "����ó�� �ִ� 20�ڱ��� �Է� �����մϴ�.")
	@NotBlank(message = "����ó�� �ʼ� �׸��Դϴ�.")
	private String phone;
}

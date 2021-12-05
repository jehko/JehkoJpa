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
	
	@Size(max = 20, message = "����ó�� �ִ� 20�ڱ��� �Է� �����մϴ�.")
	@NotBlank(message = "����ó�� �ʼ� �׸��Դϴ�.")
	private String phone;
}

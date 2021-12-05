package com.jehko.jpa.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordUtils {
	// �н����� ��ȣȭ ���� �Լ�
	// �Է��� �н����带 �ؽõ� �н������ ��
	public static boolean equalPassword(String password, String encryptedPassword) {
		return BCrypt.checkpw(password, encryptedPassword);
	}
}

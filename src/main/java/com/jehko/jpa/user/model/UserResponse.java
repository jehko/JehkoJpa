package com.jehko.jpa.user.model;

import com.jehko.jpa.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
	private long id;

	private String email;

	private String userName;

	private String phone;

	public static UserResponse of(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.phone(user.getPhone()).build();
	}
}
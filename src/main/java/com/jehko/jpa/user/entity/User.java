package com.jehko.jpa.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String email;

	@Column
	private String userName;

	@Column
	private String password;

	@Column
	private String phone;
	
	@Column
	private boolean deleted;

	@Column
	private LocalDateTime regDate;

	@Column
	private LocalDateTime updateDate;
}

package com.jehko.jpa.user.entity;

import com.jehko.jpa.user.model.UserPointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class UserPoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn
	private User user;

	@Enumerated(EnumType.STRING)
	@Column
	private UserPointType userPointType;

	@Column
	private int point;

}

package com.jehko.jpa.board.entity;

import com.jehko.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn
	private User user;

	@ManyToOne
	@JoinColumn
	private BoardType boardType;

	@Column
	private String title;

	@Column
	private String contents;

	@Column
	private LocalDateTime regDate;

	@Column
	private LocalDateTime updateDate;

	@Column
	private boolean topYn;

	@Column
	private LocalDate publishStartDate;

	@Column
	private LocalDate publishEndDate;
}

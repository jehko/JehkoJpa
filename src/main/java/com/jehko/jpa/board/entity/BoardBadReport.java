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
public class BoardBadReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn
	private User user;

	@Column
	private String comments;

	@Column
	private long boardId;

	@Column
	private long boardUserId;

	@Column
	private String boardTitle;

	@Column
	private String boardContents;

	@Column
	private LocalDateTime boardRegDate;

	@Column
	private LocalDateTime regDate;
}

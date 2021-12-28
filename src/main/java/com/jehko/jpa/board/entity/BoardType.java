package com.jehko.jpa.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String boardName;

	@Column
	private LocalDateTime regDate;

	@Column
	private LocalDateTime updateDate;

	@Column
	private boolean usingYn;
}

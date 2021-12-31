package com.jehko.jpa.board.model;

import com.jehko.jpa.board.entity.BoardBadReport;
import com.jehko.jpa.board.entity.BoardScrap;
import com.jehko.jpa.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardScrapResponse {
	private UserResponse user;
	private BoardResponse board;

	public static BoardScrapResponse of(BoardScrap boardScrap) {
		return BoardScrapResponse.builder()
				.user(UserResponse.of(boardScrap.getUser()))
				.board(BoardResponse.of(boardScrap.getBoard()))
				.build();
	}
}

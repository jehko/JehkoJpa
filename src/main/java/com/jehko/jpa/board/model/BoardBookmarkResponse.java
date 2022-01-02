package com.jehko.jpa.board.model;

import com.jehko.jpa.board.entity.BoardBookmark;
import com.jehko.jpa.board.entity.BoardScrap;
import com.jehko.jpa.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardBookmarkResponse {
	private UserResponse user;
	private BoardResponse board;

	public static BoardBookmarkResponse of(BoardBookmark boardBookmark) {
		return BoardBookmarkResponse.builder()
				.user(UserResponse.of(boardBookmark.getUser()))
				.board(BoardResponse.of(boardBookmark.getBoard()))
				.build();
	}
}

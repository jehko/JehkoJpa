package com.jehko.jpa.board.model;

import com.jehko.jpa.board.entity.BoardBadReport;
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
public class BoardBadReportResponse {
	private UserResponse user;
	private String comments;

	private long boardId;
	private long boardUserId;
	private String boardTitle;
	private String boardContents;
	private LocalDateTime boardRegDate;

	private LocalDateTime regDate;

	public static BoardBadReportResponse of(BoardBadReport boardBadReport) {
		return BoardBadReportResponse.builder()
				.user(UserResponse.of(boardBadReport.getUser()))
				.comments(boardBadReport.getComments())
				.boardId(boardBadReport.getBoardId())
				.boardUserId(boardBadReport.getBoardUserId())
				.boardTitle(boardBadReport.getBoardTitle())
				.boardContents(boardBadReport.getBoardContents())
				.boardRegDate(boardBadReport.getBoardRegDate())
				.regDate(boardBadReport.getRegDate())
				.build();
	}
}

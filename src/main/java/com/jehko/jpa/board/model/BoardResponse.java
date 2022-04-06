package com.jehko.jpa.board.model;

import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardType;
import com.jehko.jpa.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private UserResponse user;
    private long boardTypeId;
    private String boardTypeName;
    private String title;
    private String contents;
    private LocalDateTime regDate;

    public static BoardResponse of(Board board) {
        return BoardResponse.builder()
                .user(UserResponse.of(board.getUser()))
                .boardTypeId(board.getBoardType().getId())
                .boardTypeName(board.getBoardType().getBoardName())
                .title(board.getTitle())
                .contents(board.getContents())
                .regDate(board.getRegDate())
                .build();
    }
}

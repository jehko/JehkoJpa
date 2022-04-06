package com.jehko.jpa.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardTypeCount {

    private long id;
    private String boardName;
    private boolean usingYn;
    private LocalDateTime regDate;

    private long boardCount;

    public BoardTypeCount(BigInteger id, String boardName, Boolean usingYn, Timestamp regDate, BigInteger boardCount) {
        this.id = id.longValue();
        this.boardName = boardName;
        this.usingYn = usingYn;
        this.regDate = regDate.toLocalDateTime();
        this.boardCount = boardCount.longValue();
    }
}

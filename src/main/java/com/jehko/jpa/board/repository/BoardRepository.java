package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    long countByBoardType(BoardType boardType);
}

package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardType;
import com.jehko.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    long countByBoardType(BoardType boardType);

    List<Board> findByUser(User user);
}

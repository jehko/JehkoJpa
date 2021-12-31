package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardScrap;
import com.jehko.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {
    long countByBoardAndUser(Board board, User user);

    List<BoardScrap> findByUser(User user);
}

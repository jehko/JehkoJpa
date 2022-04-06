package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardBookmark;
import com.jehko.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {
    long countByBoardAndUser(Board board, User user);

    List<BoardBookmark> findByUser(User user);
}

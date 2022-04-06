package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.BoardComment;
import com.jehko.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findByUser(User user);
}

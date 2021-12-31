package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.BoardBadReport;
import com.jehko.jpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardBadReportRepository extends JpaRepository<BoardBadReport, Long> {
    long countByUserAndBoardId(User user, long boardId);
}

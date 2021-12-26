package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {

    BoardType findByBoardName(String name);
}

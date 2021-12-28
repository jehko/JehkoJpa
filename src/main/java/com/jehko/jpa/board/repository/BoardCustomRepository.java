package com.jehko.jpa.board.repository;

import com.jehko.jpa.board.model.BoardTypeCount;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardCustomRepository {
	private final EntityManager entityManager;

	public List<BoardTypeCount> findBoardTypeCount() {
		String sql = "select b.id, b.board_name, b.using_yn, b.reg_date, (select count(*) from Board where board_type_id = b.id) board_count from board_type b";

		Query nativeQuery = entityManager.createNativeQuery(sql);
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		List<BoardTypeCount> resultList = jpaResultMapper.list(nativeQuery, BoardTypeCount.class);

		return resultList;
	}
}

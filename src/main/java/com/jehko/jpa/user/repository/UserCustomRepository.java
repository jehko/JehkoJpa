package com.jehko.jpa.user.repository;

import com.jehko.jpa.user.model.UserNoticeCount;
import com.jehko.jpa.user.model.UserNoticeLogCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserCustomRepository {
	private final EntityManager entityManager;

	public List<UserNoticeCount> findUserNoticeCount() {
		String sql = "select u.id, u.email, u.user_name, (select count(*) from Notice n where n.user_id = u.id) notice_count from User u";

		return entityManager.createNativeQuery(sql).getResultList();
	}

	public List<UserNoticeLogCount> findUserNoticeLogCount() {
		String sql = "select u.id, u.email, u.user_name, " +
				"(select count(*) from notice n where n.user_id = u.id) notice_count, " +
				"(select count(*) from notice_like n where n.user_id = u.id) notice_like_count " +
				"from user u";

		return entityManager.createNativeQuery(sql).getResultList();
	}

	public List<UserNoticeLogCount> findUserNoticeLikeBest() {
		String sql = "select u.id, u.email, u.user_name, " +
				"(select count(1) from notice_like where user_id = u.id) notice_like_count " +
				"from user u " +
				"order by notice_like_count desc " +
				"limit 10";

		return entityManager.createNativeQuery(sql).getResultList();
	}
}

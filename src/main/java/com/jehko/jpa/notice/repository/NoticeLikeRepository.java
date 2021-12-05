package com.jehko.jpa.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jehko.jpa.notice.entity.NoticeLike;
import com.jehko.jpa.user.entity.User;

@Repository
public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {
	List<NoticeLike> findByUser(User user);
}

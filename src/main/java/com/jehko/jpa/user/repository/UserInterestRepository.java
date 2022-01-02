package com.jehko.jpa.user.repository;

import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
	long countByUserAndInterestUser(User user, User interestUser);
}

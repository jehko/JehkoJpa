package com.jehko.jpa.logs.repository;

import com.jehko.jpa.logs.entity.Logs;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
}

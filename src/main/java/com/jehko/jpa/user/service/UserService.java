package com.jehko.jpa.user.service;

import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.model.UserNoticeCount;
import com.jehko.jpa.user.model.UserStatus;
import com.jehko.jpa.user.model.UserSummary;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSummary getUserStatusCount() {
        return UserSummary.builder()
                .usingUserCount(userRepository.countByStatus(UserStatus.Using))
                .stopUserCount(userRepository.countByStatus(UserStatus.Stop))
                .lockUserCount(userRepository.countByStatus(UserStatus.Lock))
                .totalUserCount(userRepository.count())
                .build();
    }

    public List<User> getTodayUsers() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        return userRepository.findTodayRegUser(startDate, endDate);
    }

    public List<UserNoticeCount> getUserNoticeCount() {

        return userRepository.findUserNoticeCount();
    }
}

package com.jehko.jpa.user.service;

import com.jehko.jpa.user.model.UserStatus;
import com.jehko.jpa.user.model.UserSummary;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

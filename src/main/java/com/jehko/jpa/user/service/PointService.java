package com.jehko.jpa.user.service;

import com.jehko.jpa.common.model.ServiceResult;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.entity.UserPoint;
import com.jehko.jpa.user.model.UserPointInput;
import com.jehko.jpa.user.repository.UserPointRepository;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;

    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        User user = optionalUser.get();

        userPointRepository.save(UserPoint.builder()
                .user(user)
                .userPointType(userPointInput.getUserPointType())
                .point(userPointInput.getUserPointType().getValue())
                .build());

        return ServiceResult.success();
    }
}

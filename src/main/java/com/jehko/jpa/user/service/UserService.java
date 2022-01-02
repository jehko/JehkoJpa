package com.jehko.jpa.user.service;

import com.jehko.jpa.board.model.ServiceResult;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.entity.UserInterest;
import com.jehko.jpa.user.model.UserNoticeCount;
import com.jehko.jpa.user.model.UserNoticeLogCount;
import com.jehko.jpa.user.model.UserStatus;
import com.jehko.jpa.user.model.UserSummary;
import com.jehko.jpa.user.repository.UserCustomRepository;
import com.jehko.jpa.user.repository.UserInterestRepository;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;



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
        return userCustomRepository.findUserNoticeCount();
    }

    public List<UserNoticeLogCount> getUserNoticeLogCount() {
        return userCustomRepository.findUserNoticeLogCount();
    }

    public List<UserNoticeLogCount> getNoticeLikeBest() {
        return userCustomRepository.findUserNoticeLikeBest();
    }

    public ServiceResult addInterestUser(Long id, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if(!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("관심 사용자에 추가할 회원 정보가 없습니다.");
        }

        User user = optionalUser.get();
        User interestUser = optionalInterestUser.get();

        if(user.getId() == interestUser.getId()) {
            return ServiceResult.fail("자기 자신은 관심 사용자에 추가할 수 없습니다.");
        }

        if(userInterestRepository.countByUserAndInterestUser(user, interestUser) > 0) {
            return ServiceResult.fail("이미 관심 사용자에 추가한 회원입니다.");
        }

        userInterestRepository.save(UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    public ServiceResult removeInterestUser(Long id, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Optional<UserInterest> optionalUserInterest = userInterestRepository.findById(id);
        if(!optionalUserInterest.isPresent()) {
            return ServiceResult.fail("관심 사용자에 등록된 회원이 아닙니다.");
        }

        User user = optionalUser.get();
        UserInterest userInterest = optionalUserInterest.get();

        if(userInterest.getUser().getId() != user.getId()) {
            return ServiceResult.fail("본인의 관심 사용자만 삭제할 수 있습니다.");
        }

        userInterestRepository.delete(userInterest);

        return ServiceResult.success();
    }
}

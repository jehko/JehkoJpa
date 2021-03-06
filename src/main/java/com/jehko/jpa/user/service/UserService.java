package com.jehko.jpa.user.service;

import com.jehko.jpa.common.exception.BizException;
import com.jehko.jpa.common.model.ServiceResult;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.entity.UserInterest;
import com.jehko.jpa.user.model.*;
import com.jehko.jpa.user.repository.UserCustomRepository;
import com.jehko.jpa.user.repository.UserInterestRepository;
import com.jehko.jpa.user.repository.UserRepository;
import com.jehko.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            return ServiceResult.fail("????????? ????????? ???????????? ????????????.");
        }

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if(!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("?????? ???????????? ????????? ?????? ????????? ????????????.");
        }

        User user = optionalUser.get();
        User interestUser = optionalInterestUser.get();

        if(user.getId() == interestUser.getId()) {
            return ServiceResult.fail("?????? ????????? ?????? ???????????? ????????? ??? ????????????.");
        }

        if(userInterestRepository.countByUserAndInterestUser(user, interestUser) > 0) {
            return ServiceResult.fail("?????? ?????? ???????????? ????????? ???????????????.");
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
            return ServiceResult.fail("????????? ????????? ???????????? ????????????.");
        }

        Optional<UserInterest> optionalUserInterest = userInterestRepository.findById(id);
        if(!optionalUserInterest.isPresent()) {
            return ServiceResult.fail("?????? ???????????? ????????? ????????? ????????????.");
        }

        User user = optionalUser.get();
        UserInterest userInterest = optionalUserInterest.get();

        if(userInterest.getUser().getId() != user.getId()) {
            return ServiceResult.fail("????????? ?????? ???????????? ????????? ??? ????????????.");
        }

        userInterestRepository.delete(userInterest);

        return ServiceResult.success();
    }

    public User login(UserLogin userLogin) {
        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if(!optionalUser.isPresent()) {
            throw new BizException("?????? ????????? ???????????? ????????????.");
        }

        User user = optionalUser.get();
        if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new BizException("?????? ????????? ???????????? ????????????.");
        }

        return user;
    }
}

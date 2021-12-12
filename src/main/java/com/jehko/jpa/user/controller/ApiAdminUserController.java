package com.jehko.jpa.user.controller;

import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.exception.ExistEmailException;
import com.jehko.jpa.user.exception.PasswordNotMatchException;
import com.jehko.jpa.user.exception.UserNotFoundException;
import com.jehko.jpa.user.model.ResponseMessage;
import com.jehko.jpa.user.model.UserSearch;
import com.jehko.jpa.user.model.UserStatusInput;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiAdminUserController {

    private final UserRepository userRepository;

//	@GetMapping("/api/admin/user")
//	public ResponseMessage userList() {
//		List<User> userList = userRepository.findAll();
//		long totalUserCount = userRepository.count();
//
//		return ResponseMessage.builder()
//				.totalCount(totalUserCount)
//				.data(userList)
//				.build();
//	}

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(ResponseMessage.success(user));
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch) {
		List<User> userList =
				userRepository.findByEmailContainsOrPhoneContainsOrUserNameContains(userSearch.getEmail(),
						userSearch.getPhone(),
						userSearch.getUserName());

		return ResponseEntity.ok().body(ResponseMessage.success(userList));
	}

	@PatchMapping("/api/admin/user/{id}/status")
	public ResponseEntity<?> userStatus(@PathVariable Long id, @RequestBody UserStatusInput userStatusInput) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();

        user.setStatus(userStatusInput.getStatus());
        userRepository.save(user);

	    return ResponseEntity.ok().build();
    }


    @ExceptionHandler(value = {ExistEmailException.class, PasswordNotMatchException.class,
            UserNotFoundException.class})
    public ResponseEntity<String> RuntimeExceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

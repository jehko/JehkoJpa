package com.jehko.jpa.user.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jehko.jpa.notice.entity.Notice;
import com.jehko.jpa.notice.entity.NoticeLike;
import com.jehko.jpa.notice.model.NoticeResponse;
import com.jehko.jpa.notice.model.ResponseError;
import com.jehko.jpa.notice.repository.NoticeLikeRepository;
import com.jehko.jpa.notice.repository.NoticeRepository;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.exception.ExistEmailException;
import com.jehko.jpa.user.exception.PasswordNotMatchException;
import com.jehko.jpa.user.exception.UserNotFoundException;
import com.jehko.jpa.user.model.UserInput;
import com.jehko.jpa.user.model.UserInputFind;
import com.jehko.jpa.user.model.UserInputPassword;
import com.jehko.jpa.user.model.UserLogin;
import com.jehko.jpa.user.model.UserLoginToken;
import com.jehko.jpa.user.model.UserResponse;
import com.jehko.jpa.user.model.UserUpdate;
import com.jehko.jpa.user.repository.UserRepository;
import com.jehko.jpa.util.MailService;
import com.jehko.jpa.util.PasswordUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiUserController {

	private final UserRepository userRepository;
	private final NoticeRepository noticeRepository;
	private final NoticeLikeRepository noticeLikeRepository;
	private final MailService mailService;

//	@PostMapping("/api/user")
//	public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {
//		List<ResponseError> responseErrors = new ArrayList<>();
//
//		if (errors.hasErrors()) {
//			errors.getAllErrors().forEach((e) -> {
//				responseErrors.add(ResponseError.of((FieldError) e));
//			});
//
//			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
//		}
//
//		User user = User.builder()
//				.email(userInput.getEmail())
//				.userName(userInput.getUserName())
//				.password(userInput.getPassword())
//				.phone(userInput.getPhone())
//				.regDate(LocalDateTime.now()).build();
//
//		userRepository.save(user);
//
//		return ResponseEntity.ok().build();
//	}
	
	private String getEncryptPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
	
	@PostMapping("/api/user")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {
		List<ResponseError> responseErrors = new ArrayList<>();

		if (errors.hasErrors()) {
			errors.getAllErrors().forEach((e) -> {
				responseErrors.add(ResponseError.of((FieldError) e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}

		if(userRepository.countByEmail(userInput.getEmail()) > 0) {
			throw new ExistEmailException("이미 존재하는 이메일입니다.");
		}

		User user = User.builder()
				.email(userInput.getEmail())
				.userName(userInput.getUserName())
				.password(getEncryptPassword(userInput.getPassword()))
				.phone(userInput.getPhone())
				.regDate(LocalDateTime.now()).build();

		userRepository.save(user);

		return ResponseEntity.ok().build();
	}
	

	@PutMapping("/api/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdate userUpdate,
			Errors errors) {
		List<ResponseError> responseErrors = new ArrayList<>();

		if (errors.hasErrors()) {
			errors.getAllErrors().forEach((e) -> {
				responseErrors.add(ResponseError.of((FieldError) e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

		user.setPhone(userUpdate.getPhone());
		user.setUpdateDate(LocalDateTime.now());
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/api/user/{id}")
	public UserResponse getUser(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
		return UserResponse.of(user);
	}
	
	@GetMapping("/api/user/{id}/notice")
	public List<NoticeResponse> userNotice(@PathVariable Long id ) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다.")); 
		
		List<Notice> noticeList = noticeRepository.findByUser(user).get();
		
		List<NoticeResponse> noticeResponseList = new ArrayList<>();
		noticeList.stream().forEach((e) -> {
			noticeResponseList.add(NoticeResponse.of(e));
		});
		
		return noticeResponseList;
	}
	
	@PatchMapping("/api/user/{id}/password")
	public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody @Valid UserInputPassword userInputPassword,
			Errors errors) {
		List<ResponseError> responseErrors = new ArrayList<>();

		if (errors.hasErrors()) {
			errors.getAllErrors().forEach((e) -> {
				responseErrors.add(ResponseError.of((FieldError) e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}

		User user = userRepository.findByIdAndPassword(id, userInputPassword.getPassword())
				.orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));
		
		user.setPassword(userInputPassword.getNewPassword());
		user.setUpdateDate(LocalDateTime.now());
		userRepository.save(user);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/api/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

		try {
			userRepository.delete(user);
		} catch (DataIntegrityViolationException e) {
			String message = "제약조건에 문제가 발생했습니다.";
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String message = "회원 탈퇴 중 문제가 발생했습니다.";
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/api/user/{id}/deleted")
	public ResponseEntity<?> updateUserDeleted(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

		user.setDeleted(true);
		user.setUpdateDate(LocalDateTime.now());
		
		userRepository.save(user);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/api/user")
	public ResponseEntity<?> findUser(@RequestBody UserInputFind userInputFind) {
		User user = userRepository.findByUserNameAndPhone(userInputFind.getUserName(), userInputFind.getPhone())
				.orElseThrow(() -> new UserNotFoundException("사용자가 정보가 없습니다."));
		
		UserResponse userResponse = UserResponse.of(user);
		
		return ResponseEntity.ok().body(userResponse);
	}
	
	@GetMapping("/api/user/{id}/password/reset")
	public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
		
		// password 초기화
		String initPassword = getResetPassword();
		user.setPassword(getEncryptPassword(initPassword));
		user.setUpdateDate(LocalDateTime.now());
		userRepository.save(user);
		
//		mailService.sendMail(user.getEmail(), "패스워드 초기화 안내", "패스워드가 " + initPassword + "로 초기화 되었습니다.");
		mailService.sendMail("jehko08@naver.com", "패스워드 초기화 안내", "패스워드가 " + initPassword + "로 초기화 되었습니다.");
		
		return ResponseEntity.ok().build();
	}
	
	private String getResetPassword() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}
	
	@GetMapping("/api/user/{id}/notice/like")
	public List<NoticeLike> likeNotice(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
		
		List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);
		return noticeLikeList;
	}
	
	@PostMapping("/api/user/login")
	public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {
		List<ResponseError> responseErrors = new ArrayList<>();

		if (errors.hasErrors()) {
			errors.getAllErrors().forEach((e) -> {
				responseErrors.add(ResponseError.of((FieldError) e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}

		User user = userRepository.findByEmail(userLogin.getEmail())
				.orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
		
		if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
		
		// 토큰 만료 일자 계산 - 현재 시간에서 1개월 뒤
		Date expiredDate = Timestamp.valueOf(LocalDateTime.now().plusMonths(1));
		
		String token = JWT.create()
			.withExpiresAt(expiredDate)
			.withClaim("user_id", user.getId())
			.withSubject(user.getUserName())
			.withIssuer(user.getEmail())
			.sign(Algorithm.HMAC512("jehkojpa".getBytes()));
		
		return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
	}
	
	@PatchMapping("/api/user/login")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) {
		
		return ResponseEntity.ok().build();
	}

	
	
	@ExceptionHandler(value = { ExistEmailException.class, PasswordNotMatchException.class,
			UserNotFoundException.class })
	public ResponseEntity<String> RuntimeExceptionHandler(RuntimeException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}

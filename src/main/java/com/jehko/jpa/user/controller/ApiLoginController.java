package com.jehko.jpa.user.controller;

import com.jehko.jpa.common.exception.BizException;
import com.jehko.jpa.common.model.ResponseError;
import com.jehko.jpa.common.model.ResponseResult;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.exception.ExistEmailException;
import com.jehko.jpa.user.exception.PasswordNotMatchException;
import com.jehko.jpa.user.exception.UserNotFoundException;
import com.jehko.jpa.user.model.*;
import com.jehko.jpa.user.service.UserService;
import com.jehko.jpa.util.JWTUtils;
import com.jehko.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiLoginController {

	private final UserService userService;
	
	@PostMapping("/api/public/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserLogin userLogin, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseResult.fail("입력값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
		}

		User user = null;

		try {
			user = userService.login(userLogin);
		} catch(BizException e) {
			log.info("Login failed: {}", e.getMessage());
			return ResponseResult.fail(e.getMessage());
		}

		UserLoginToken userLoginToken = JWTUtils.createToken(user);
		if(userLoginToken == null) {
			log.info("create JWT Failed!!");
			return ResponseResult.fail("회원 정보가 존재하지 않습니다.");
		}
		
		return ResponseResult.success(userLoginToken);
	}
//
//	@PatchMapping("/api/user/login")
//	public ResponseEntity<?> refreshToken(@RequestHeader("J_TOKEN") String token) {
//		String email = "";
//
//		try {
//			email = JWTUtils.getIssuer(token);
//		} catch (SignatureVerificationException e) {
//			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
//		}
//
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
//
//		String newToken = JWTUtils.createToken(user);
//
//		return ResponseEntity.ok().body(UserLoginToken.builder().token(newToken).build());
//	}
//
//	@DeleteMapping("/api/user/login")
//	public ResponseEntity<?> removeToken(@RequestHeader("J_TOKEN") String token) {
//
//		String email = "";
//
//		try {
//			email = JWTUtils.getIssuer(token);
//		} catch(SignatureVerificationException e) {
//			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
//		}
//
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
//
//
//		return ResponseEntity.ok().build();
//	}


	@ExceptionHandler(value = { ExistEmailException.class, PasswordNotMatchException.class,
			UserNotFoundException.class })
	public ResponseEntity<String> RuntimeExceptionHandler(RuntimeException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}

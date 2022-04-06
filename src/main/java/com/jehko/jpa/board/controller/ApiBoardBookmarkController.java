package com.jehko.jpa.board.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.jehko.jpa.common.model.ServiceResult;
import com.jehko.jpa.board.service.BoardService;
import com.jehko.jpa.common.model.ResponseMessage;
import com.jehko.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardBookmarkController {

	private final BoardService boardService;

	@GetMapping("/api/board/bookmark")
	public ResponseEntity<?> boardBookmark(@RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.boardBookmarkList(email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success(result));
	}

	@PutMapping("/api/board/{id}/bookmark")
	public ResponseEntity<?> addBoardBookmark(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.addBookmark(id, email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@DeleteMapping("/api/bookmark/{id}")
	public ResponseEntity<?> deleteBoardBookmark(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.removeBookmark(id, email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}
}

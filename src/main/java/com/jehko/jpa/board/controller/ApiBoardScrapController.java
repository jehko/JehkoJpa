package com.jehko.jpa.board.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.jehko.jpa.board.model.ServiceResult;
import com.jehko.jpa.board.service.BoardService;
import com.jehko.jpa.user.model.ResponseMessage;
import com.jehko.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardScrapController {

	private final BoardService boardService;

	@GetMapping("/api/board/scrap")
	public ResponseEntity<?> boardScrap(@RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.boardScrapList(email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success(result));
	}

	@PutMapping("/api/board/{id}/scrap")
	public ResponseEntity<?> addBoardScrap(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.scrapBoard(id, email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@DeleteMapping("/api/scrap/{id}")
	public ResponseEntity<?> deleteBoardScrap(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.removeScrap(id, email);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}
}

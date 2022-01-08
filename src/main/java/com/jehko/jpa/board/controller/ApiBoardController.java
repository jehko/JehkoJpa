package com.jehko.jpa.board.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.jehko.jpa.board.entity.Board;
import com.jehko.jpa.board.entity.BoardType;
import com.jehko.jpa.board.model.*;
import com.jehko.jpa.board.service.BoardService;
import com.jehko.jpa.common.exception.BizException;
import com.jehko.jpa.common.model.ResponseError;
import com.jehko.jpa.common.model.ResponseMessage;
import com.jehko.jpa.common.model.ResponseResult;
import com.jehko.jpa.common.model.ServiceResult;
import com.jehko.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

	private final BoardService boardService;

	@PostMapping("/api/board/type")
	public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

		if (errors.hasErrors()) {
			List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
			return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.addBoard(boardTypeInput);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PutMapping("/api/board/type/{id}")
	public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

		if (errors.hasErrors()) {
			List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
			return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.updateBoard(id, boardTypeInput);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@DeleteMapping("/api/board/type/{id}")
	public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {
		ServiceResult result = boardService.deleteBoard(id);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@GetMapping("/api/board/type")
	public ResponseEntity<?> boardType() {
		List<BoardType> boardTypeList = boardService.getAllBoardType();

		return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
	}

	@PatchMapping("/api/board/type/{id}/using")
	public ResponseEntity<?> usingBoardType(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing) {
		ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@GetMapping("/api/board/type/count")
	public ResponseEntity<?> boardTypeCount() {
		List<BoardTypeCount> boardTypeCountList = boardService.getBoardTypeCount();

		return ResponseEntity.ok().body(ResponseMessage.success(boardTypeCountList));
	}

	@PostMapping("/api/board")
	public ResponseEntity<?> add(@RequestHeader("J_TOKEN") String token, @RequestBody BoardInput boardInput) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.add(email, boardInput);

		return ResponseResult.result(result);
	}

	@GetMapping("/api/board/list/{page}")
	public ResponseEntity<?> boardList(@PathVariable int page) {
		Page<Board> boardList = boardService.getBoardList(page);

		return ResponseResult.success(boardList);
	}

	@GetMapping("/api/board/{id}")
	public ResponseEntity<?> detail(@PathVariable Long id) {
		Board board = null;
		try {
			board = boardService.detail(id);
		} catch(BizException e) {
			return ResponseResult.fail(e.getMessage());
		}

		return ResponseResult.success(board);
	}

	@PatchMapping("/api/board/{id}/top")
	public ResponseEntity<?> boardPostTop(@PathVariable Long id, @RequestBody BoardTopInput boardTopInput) {
		ServiceResult result = boardService.setBoardTop(id, boardTopInput);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PatchMapping("/api/board/{id}/publish")
	public ResponseEntity<?> boardPeriod(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod) {
		ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PutMapping("/api/board/{id}/hits")
	public ResponseEntity<?> boardHits(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.setBoardHits(id, email);
		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PutMapping("/api/board/{id}/like")
	public ResponseEntity<?> boardLike(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.setBoardLike(id, email);
		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PutMapping("/api/board/{id}/unlike")
	public ResponseEntity<?> boardUnlike(@PathVariable Long id, @RequestHeader("J_TOKEN") String token) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.setBoardUnlike(id, email);
		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

	@PutMapping("/api/board/{id}/badreport")
	public ResponseEntity<?> boardBadReport(@PathVariable Long id, @RequestHeader("J_TOKEN") String token, @RequestBody BoardBadReportInput boardBadReportInput) {
		String email = "";

		try {
			email = JWTUtils.getIssuer(token);
		} catch(SignatureVerificationException e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("토큰 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		ServiceResult result = boardService.addBadReport(id, email, boardBadReportInput);
		if (!result.isResult()) {
			return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
		}

		return ResponseEntity.ok().body(ResponseMessage.success());
	}

}

package com.jehko.jpa.board.controller;

import com.jehko.jpa.board.model.BoardTypeInput;
import com.jehko.jpa.board.model.ServiceResult;
import com.jehko.jpa.board.service.BoardService;
import com.jehko.jpa.notice.model.ResponseError;
import com.jehko.jpa.user.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

}

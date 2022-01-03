package com.jehko.jpa.board.controller;

import com.jehko.jpa.board.model.BoardBadReportResponse;
import com.jehko.jpa.board.service.BoardService;
import com.jehko.jpa.common.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiAdminBoardController {
	private final BoardService boardService;

	@GetMapping("/api/admin/board/badreport")
	public ResponseEntity<?> badReport() {
		List<BoardBadReportResponse> list = boardService.badReportList();
		return ResponseEntity.ok().body(ResponseMessage.success(list));
	}
}

package com.jehko.jpa.notice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.jehko.jpa.notice.entity.Notice;
import com.jehko.jpa.notice.exception.AlreadyDeletedException;
import com.jehko.jpa.notice.exception.DuplicateNoticeException;
import com.jehko.jpa.notice.exception.NoticeNotFoundException;
import com.jehko.jpa.notice.model.NoticeDeleteInput;
import com.jehko.jpa.notice.model.NoticeInput;
import com.jehko.jpa.common.model.ResponseError;
import com.jehko.jpa.notice.repository.NoticeRepository;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

	private final NoticeRepository noticeRepository;

	@Operation(summary = "test", description="swagger test")
	@GetMapping("/api/notice/{id}")
	public Notice notice(@PathVariable Long id) {
		Optional<Notice> notice = noticeRepository.findById(id);
		if (notice.isPresent()) {
			return notice.get();
		}

		return null;
	}

	@PutMapping("/api/notice/{id}")
	public Notice updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

		Notice notice = noticeRepository.findById(id)
				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

		notice.setTitle(noticeInput.getTitle());
		notice.setContents(noticeInput.getContents());
		notice.setUpdateDate(LocalDateTime.now());

		return noticeRepository.save(notice);
	}

	@PatchMapping("/api/notice/{id}/hits")
	public void noticeHits(@PathVariable Long id) {
		Notice notice = noticeRepository.findById(id)
				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

		notice.setHits(notice.getHits() + 1);
		noticeRepository.save(notice);
	}


	@DeleteMapping("/api/notice/{id}")
	public void deleteNotice(@PathVariable Long id) {
		Notice notice = noticeRepository.findById(id)
				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

		if (notice.isDeleted()) {
			throw new AlreadyDeletedException("이미 삭제된 글입니.");
		}

		notice.setDeleted(true);
		notice.setDeletedDate(LocalDateTime.now());

		noticeRepository.save(notice);
	}

	@DeleteMapping("/api/notice")
	public void deleteNoticeList(@RequestBody NoticeDeleteInput noticeDeleteInput) {
		List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

		noticeList.stream().forEach(e -> {
			e.setDeleted(true);
			e.setDeletedDate(LocalDateTime.now());
		});

		noticeRepository.saveAll(noticeList);
	}
	

	@DeleteMapping("/api/notice/modifyAll")
	public void deleteModifyAll() {
		List<Notice> noticeList = noticeRepository.findAll();
		
		noticeList.stream().forEach(e -> {
			e.setDeleted(true);
			e.setDeletedDate(LocalDateTime.now());
		});

		noticeRepository.saveAll(noticeList);		
	}

	@DeleteMapping("/api/notice/all")
	public void deleteAll() {
		noticeRepository.deleteAll();
	}

	@PostMapping("/api/notice")
	public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput, Errors errors) {
		// ��ȿ�� üũ
		if (errors.hasErrors()) {
			List<ResponseError> responseErrors = new ArrayList<>();
			errors.getAllErrors().stream().forEach(e -> {
				responseErrors.add(ResponseError.of((FieldError)e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}
		
		// �ߺ� ��� ����
		// ���� �ð� - 1��
		LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);
		// 1�� �̳��� ���� ����, ���� �������� ��ϵ� �Խñ��� �ִ��� Ȯ��
		int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
				noticeInput.getTitle(), noticeInput.getContents(), checkDate);
		if(noticeCount > 0) {
			throw new DuplicateNoticeException("1분 이내에 등록된 동일한 공지사항이 존재합니다.");
		}

		noticeRepository.save(Notice.builder()
					.title(noticeInput.getTitle())
					.contents(noticeInput.getContents())
					.regDate(LocalDateTime.now())
					.hits(0)
					.likes(0).build());

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/api/notice/latest/{size}")
	public Page<Notice> noticeLatest(@PathVariable int size) {
		return noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));
	}
	
	@ExceptionHandler(DuplicateNoticeException.class)
	public ResponseEntity<String> handlerDuplicateNoticeException(DuplicateNoticeException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoticeNotFoundException.class)
	public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AlreadyDeletedException.class)
	public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	}
}

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
import com.jehko.jpa.notice.model.ResponseError;
import com.jehko.jpa.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

	private final NoticeRepository noticeRepository;

//	@GetMapping("/api/notice")
//	public NoticeModel notice() {
//		NoticeModel notice = new NoticeModel();
//
//		notice.setId(1);
//		notice.setTitle("공지사항입니다.");
//		notice.setContents("공지사항 내용입니다.");
//		notice.setRegDate(LocalDateTime.of(2021, 2, 8, 0, 0));
//
//		return notice;
//	}

//	@GetMapping("/api/notice")
//	public List<NoticeModel> notice() {
//		List<NoticeModel> noticeList = new ArrayList<>();
//		
//		noticeList.add(NoticeModel.builder()
//				.id(1)
//				.title("공지사항입니다.")
//				.contents("공지사항 내용입니다.")
//				.regDate(LocalDateTime.of(2021, 1, 30, 0, 0)).build());
//		
//		noticeList.add(NoticeModel.builder()
//				.id(2)
//				.title("두번째 공지사항입니다..")
//				.contents("두번재 공지사항 내용입니다.")
//				.regDate(LocalDateTime.of(2021, 2, 8, 0, 0)).build());
//
//		
//		return noticeList;
//	}

//	@GetMapping("/api/notice")
//	public List<NoticeModel> notice() {
//		List<NoticeModel> noticeList = new ArrayList<>();
//
//		return noticeList;
//	}

	@GetMapping("/api/notice/count")
	public int noticeCount() {
		return 2;
	}

//	@PostMapping("/api/notice")
//	public NoticeModel addNotice(String title, String contents) {
//		NoticeModel notice = NoticeModel.builder().id(1).title(title).contents(contents).regDate(LocalDateTime.now())
//				.build();
//
//		return notice;
//
//	}

//	@PostMapping("/api/notice")
//	public NoticeModel addNotice(NoticeModel noticeModel) {
//		
//		noticeModel.setId(2);
//		noticeModel.setRegDate(LocalDateTime.now());
//		
//		return noticeModel;
//		
//	}

//	@PostMapping("/api/notice")
//	public NoticeModel addNotice(@RequestBody NoticeModel noticeModel) {
//		
//		noticeModel.setId(3);
//		noticeModel.setRegDate(LocalDateTime.now());
//		
//		return noticeModel;
//		
//	}

//	@PostMapping("/api/notice")
//	public Notice addNotice(@RequestBody NoticeInput noticeInput) {
//		
//		Notice notice = Notice.builder()
//				.title(noticeInput.getTitle())
//				.contents(noticeInput.getContents())
//				.regDate(LocalDateTime.now())
//				.build();
//		
//		return noticeRepository.save(notice);
//	}

//	@PostMapping("/api/notice")
//	public Notice addNotice(@RequestBody NoticeInput noticeInput) {
//
//		Notice notice = Notice.builder().title(noticeInput.getTitle()).contents(noticeInput.getContents())
//				.regDate(LocalDateTime.now()).hits(0).likes(0).build();
//
//		return noticeRepository.save(notice);
//	}

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

	/**
	 * 데이터를 삭제
	 * 
	 * @param id
	 */
//	@DeleteMapping("/api/notice/{id}")
//	public void deleteNotice(@PathVariable Long id) {
//		Notice notice = noticeRepository.findById(id)
//				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
//		
//		noticeRepository.delete(notice);
//	}

	@DeleteMapping("/api/notice/{id}")
	public void deleteNotice(@PathVariable Long id) {
		Notice notice = noticeRepository.findById(id)
				.orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

		if (notice.isDeleted()) {
			throw new AlreadyDeletedException("이미 삭제된 글입니다.");
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
		// 유효성 체크
		if (errors.hasErrors()) {
			List<ResponseError> responseErrors = new ArrayList<>();
			errors.getAllErrors().stream().forEach(e -> {
				responseErrors.add(ResponseError.of((FieldError)e));
			});

			return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
		}
		
		// 중복 등록 방지
		// 현재 시간 - 1분
		LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);
		// 1분 이내에 같은 제목, 같은 내용으로 등록된 게시글이 있는지 확인
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

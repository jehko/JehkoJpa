package com.jehko.jpa.notice.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeModel {

	// ID, 제목, 내용, 등록일(작성일)
	private int id;
	private String title;
	private String contents;
	private LocalDateTime regDate;
}

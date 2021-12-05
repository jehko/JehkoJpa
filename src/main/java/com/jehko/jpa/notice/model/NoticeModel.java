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

	// ID, ����, ����, �����(�ۼ���)
	private int id;
	private String title;
	private String contents;
	private LocalDateTime regDate;
}

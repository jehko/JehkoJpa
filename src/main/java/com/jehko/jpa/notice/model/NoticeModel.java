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

	// ID, 力格, 郴侩, 殿废老(累己老)
	private int id;
	private String title;
	private String contents;
	private LocalDateTime regDate;
}

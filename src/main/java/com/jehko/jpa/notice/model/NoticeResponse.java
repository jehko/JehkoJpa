package com.jehko.jpa.notice.model;

import java.time.LocalDateTime;

import com.jehko.jpa.notice.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeResponse {

	private long id;
	
	private long regUserId;
	private String regUserName;
	
	private String title;
	private String contents;
	
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
	private int hits;
	private int likes;
	
	public static NoticeResponse of(Notice notice) {
		
		return NoticeResponse.builder()
				.id(notice.getId())
				.title(notice.getTitle())
				.contents(notice.getContents())
				.regDate(notice.getRegDate())
				.regUserId(notice.getUser().getId())
				.regUserName(notice.getUser().getUserName())
				.updateDate(notice.getUpdateDate())
				.hits(notice.getHits())
				.likes(notice.getLikes())
				.build();
	}
}

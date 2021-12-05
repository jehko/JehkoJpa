package com.jehko.jpa.notice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeInput {

	@Size(min = 10, max = 100, message = "������ 10-100�� �̻��Դϴ�.")
	@NotBlank(message = "������ �ʼ� �׸��Դϴ�.")
	private String title;

	@Size(min = 50, max = 1000, message = "������ 50-1000�� �̻��Դϴ�.")
	@NotBlank(message = "������ �ʼ� �׸��Դϴ�.")
	private String contents;
}

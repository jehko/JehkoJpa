package com.jehko.jpa.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNoticeCount {
    private long id;
    private String email;
    private String userName;
    private long noticeCount;
}
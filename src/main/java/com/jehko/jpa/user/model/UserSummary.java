package com.jehko.jpa.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {

    private long stopUserCount;
    private long usingUserCount;
    private long lockUserCount;
    private long totalUserCount;
}

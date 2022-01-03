package com.jehko.jpa.logs.service;

import com.jehko.jpa.logs.entity.Logs;
import com.jehko.jpa.logs.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LogService {
    private final LogsRepository logsRepository;

    public void add(String path, long userId) {
        logsRepository.save(Logs.builder()
                .path(path)
                .userId(userId)
                .regDate(LocalDateTime.now())
                .build());
    }
}

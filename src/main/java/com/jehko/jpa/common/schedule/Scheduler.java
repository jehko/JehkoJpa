package com.jehko.jpa.common.schedule;

import com.jehko.jpa.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MailService mailService;

    @Scheduled(cron = "* * 4 * * *")
    public void sendMail() {
        System.out.println("send email by scheduler");
    }
}

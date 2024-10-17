package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
@EnableScheduling
public class YourService {

    @Autowired
    private EmailService emailService;

    public void sendSampleEmail() {
        String recipientEmail = "zcwxzsx@naver.com"; // 받는 사람 이메일
        String subject = "테스트 이메일";
        String body = "안녕하세요! 이 이메일은 테스트입니다.";

        emailService.sendEmail(recipientEmail, subject, body);
    }


    @Scheduled(cron = "0 5 14 * * ?")
    public void scheduledEmail() {
        String to = "zcwxzsx@naver.com";
        String subject = "정기 이메일";
        String body = "이 이메일은 배치를실패해서 그냥스케쥴로 전송됩니다.";

        emailService.sendEmail(to, subject, body);

    }
}

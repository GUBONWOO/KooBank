package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YourService {

    @Autowired
    private EmailService emailService;

    public void sendSampleEmail() {
        String recipientEmail = "zcwxzsx@naver.com"; // 받는 사람 이메일
        String subject = "테스트 이메일";
        String body = "안녕하세요! 이 이메일은 테스트입니다.";

        emailService.sendEmail(recipientEmail, subject, body);
    }
}

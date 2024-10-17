package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String email; // 발신자 이메일

    @Value("${spring.mail.password}")
    private String password; // 발신자 이메일 비밀번호

    public void sendEmail(String to, String subject, String body) {
        // SMTP 서버 정보
        String host = "smtp.gmail.com"; // SMTP 서버 주소

        // SMTP 서버 속성 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS 사용

        // 세션 생성
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            // 이메일 메시지 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // 이메일 전송
            Transport.send(message);
            System.out.println("이메일이 성공적으로 전송되었습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
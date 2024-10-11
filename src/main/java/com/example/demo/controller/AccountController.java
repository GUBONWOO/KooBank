package com.example.demo.controller;
import com.example.demo.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create-account")
    public String createAccount(HttpSession session) {
        // 계좌번호 생성 및 세션을 사용한 계좌 저장
        String accountNumber = accountService.generateAccountNumber();
        accountService.saveAccount(accountNumber, session);

        // 계좌 생성 후 홈 페이지로 리다이렉트
        return "redirect:/home";
    }
}

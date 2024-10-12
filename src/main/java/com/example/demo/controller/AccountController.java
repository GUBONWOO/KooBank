package com.example.demo.controller;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String accountNumber) {
        if (!accountNumber.isEmpty()) {
            accountService.deleteAccount(accountNumber);
        }
        // 삭제 후 홈 페이지로 리다이렉트
        return "redirect:/home";
    }


    @PostMapping("/createAccount")
    public String createAccount(HttpSession session) {
        accountService.saveAccount(session);

        return "redirect:/home";
    }
}

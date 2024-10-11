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

    @GetMapping("/show")
    public String showAccounts(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        List<Account> accounts = accountService.getAccountsAll(loggedInUser.getName());
        model.addAttribute("accounts", accounts);

        return "home";  //
    }



    @PostMapping("/createAccount")
    public String createAccount(HttpSession session) {
        // 계좌번호 생성 및 세션을 사용한 계좌 저장
        String accountNumber = accountService.generateAccountNumber();
        accountService.saveAccount(accountNumber, session);

        // 계좌 생성 후 홈 페이지로 리다이렉트
        return "redirect:/home";
    }
}

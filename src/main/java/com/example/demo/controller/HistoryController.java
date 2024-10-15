package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.History;
import com.example.demo.service.AccountService;
import com.example.demo.service.HistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final AccountService accountService;

    // 특정 계좌의 트랜잭션 기록 조회
    @GetMapping("/history")
    public String logTransactionHistory(HttpSession session,
                                        @RequestParam String recipientAccount,
                                        @RequestParam Long amount,
                                        @RequestParam int status, Model model) {

        Account selectedAccount = (Account) session.getAttribute("selectedAccount");

        List<History> historyList = historyService.logTransactionHistory(selectedAccount, recipientAccount, amount, status);
        model.addAttribute("historyList", historyList);
        return "tasks/home";
    }

}
package com.example.demo.controller;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String accountNumber,HttpSession session) {
        if (!accountNumber.isEmpty()) {
            accountService.deleteAccount(accountNumber);
        }
        session.removeAttribute("selectedAccount");


        return "redirect:/home";
    }


    @PostMapping("/createAccount")
    public String createAccount(HttpSession session) {
        accountService.saveAccount(session);

        return "redirect:/home";
    }
    @PostMapping("/selectAccount")
    public String selectAccount(@RequestParam String accountNumber, HttpSession session) {

        Account selectedAccount = accountService.findByNumber(accountNumber);


        session.setAttribute("selectedAccount", selectedAccount);

        return "redirect:/home";
    }
    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber, @RequestParam int amount, HttpSession session) {

        accountService.deposit(accountNumber, amount);


        Account updatedAccount = accountService.findByNumber(accountNumber);
        session.setAttribute("selectedAccount", updatedAccount);


        return "redirect:/home";
    }

}

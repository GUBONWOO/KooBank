package com.example.demo.controller;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


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
    public String deposit(@RequestParam String accountNumber, @RequestParam long amount, HttpSession session) {
        accountService.deposit(accountNumber, amount);
        Account updatedAccount = accountService.findByNumber(accountNumber);
        session.setAttribute("selectedAccount", updatedAccount);
        return "redirect:/home";
    }
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber, @RequestParam long amount, HttpSession session) {
        accountService.withdraw(accountNumber, amount);
        Account updatedAccount = accountService.findByNumber(accountNumber);
        session.setAttribute("selectedAccount", updatedAccount);
        return "redirect:/home";
    }
    @PostMapping("/transaction")
    public String transaction(@RequestParam String accountNumber, @RequestParam long amount
            ,@RequestParam String transactionNumber ,HttpSession session) {
             accountService.transaction( accountNumber, amount,transactionNumber);
//        session.setAttribute("transactionAccounts", result);

        return "redirect:/home";
    }
}

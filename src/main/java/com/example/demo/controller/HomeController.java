package com.example.demo.controller;


import com.example.demo.entity.Account;
import com.example.demo.entity.History;
import com.example.demo.entity.Users;
import com.example.demo.service.AccountService;
import com.example.demo.service.HistoryService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;



@RequiredArgsConstructor
@Controller
public class HomeController {
    private final AccountService accountService;
    private final UserService userService;
    private final HistoryService historyService;


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/register")
    public String register() {
        return "tasks/register";
    }


    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
            Users loggedInUser = (Users) session.getAttribute("loggedInUser");
            List<Account> accounts = accountService.getAccountsAll(loggedInUser.getId());
            model.addAttribute("accounts", accounts);

             Account selectedAccount = (Account) session.getAttribute("selectedAccount");

             if (selectedAccount != null) {
                 Account current = accountService.findByNumber(selectedAccount.getNumber() );
                 model.addAttribute("selectedAccount", current);

//
                 List<History> historyList = historyService.findByAccount(current);
                 model.addAttribute("historyList", historyList);
             }


            return "tasks/home";

    }
}

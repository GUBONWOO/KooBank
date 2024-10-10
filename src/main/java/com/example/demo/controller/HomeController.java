package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/deposit")
    public String deposit() {
        return "tasks/deposit";
    }

    @GetMapping("/withdraw")
    public String withdraw() {
        return "tasks/withdraw";
    }

    @GetMapping("/transfer")
    public String transfer() {
        return "tasks/transfer";
    }
    @GetMapping("/history")
    public String history() {
        return "tasks/history";
    }
    @GetMapping("/register")
    public String register() {
        return "tasks/register";
    }
    @GetMapping("/home")
    public String home() {
        return "tasks/home";
    }
}

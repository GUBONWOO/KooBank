package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HomeController homeController;


    @PostMapping("/register")
    public String createUser(@Validated @ModelAttribute Users user, Model model) {
        // 아이디 중복 확인
        if (userService.checkDuplicateUser(user.getName())) {
            model.addAttribute("error1", "중복불가");
            return homeController.register(model); // 중복되면 다시 회원가입 페이지로
        }
        String password = user.getPassword();
        model.addAttribute("error2", "비밀번호는 최소 8자 이상");
        if(password.length()<8) {
            return homeController.register(model);
        }

        // 중복이 없으면 회원가입 진행
        userService.createUser(user);
        return "redirect:/"; // 회원가입 성공 후 홈 페이지로 리다이렉트
    }

}

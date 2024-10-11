package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;


    // 로그인 요청 처리
    @PostMapping("/login")
    public String loginUser(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        Users user = new Users();
        user.setName(name);
        user.setPassword(password);

        // 로그인 확인
        Optional<Users> loggedInUser = userService.checkIf(user);

        if (loggedInUser.isPresent()) {
            session.setAttribute("loggedInUser", loggedInUser.get());  // 세션에 로그인한 사용자 저장
            return "redirect:/home";  // 로그인 성공 시 홈 페이지로 리다이렉트
        } else {
            return "redirect:/";  // 로그인 실패 시 에러 메시지와 함께 로그인 페이지로 리다이렉트
        }


    }
    @PostMapping ("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping("/register")
    public String createUser(@Validated Users user) {
        userService.createUser(user);
        return "redirect:/";
    }

}

package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;



    @PostMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:"; // 로그인 페이지로 리다이렉트
    }

    @PostMapping("/register")
    public String createUser(@Validated @ModelAttribute Users user, Model model) {
        // 아이디 중복 확인
        if (userService.checkDuplicateUser(user.getName())) {
            model.addAttribute("error", "중복된 아이디가 있습니다");
            return "tasks/register" ;// 중복되면 다시 회원가입 페이지로
        }


        // 중복이 없으면 회원가입 진행
        userService.createUser(user);
        return "redirect:/"; // 회원가입 성공 후 홈 페이지로 리다이렉트
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        Users user = new Users();
        user.setName(name);
        user.setPassword(password);


        Optional<Users> loggedInUser = userService.checkIf(user);

        if (loggedInUser.isPresent()) {
            session.setAttribute("loggedInUser", loggedInUser.get());  // 세션에 로그인한 사용자 저장
            session.setAttribute("loggedInUser", loggedInUser.get());
            return "redirect:/home";
        } else {
            return "redirect:/";

        }
    }}
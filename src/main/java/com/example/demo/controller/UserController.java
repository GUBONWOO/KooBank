
package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final HomeController homeController ;





    // POST 요청을 처리하여 데이터베이스에 저장
    @PostMapping("/register")
    public String createUser(@Validated Users user) {

        userService.createUser(user);
        return "redirect:/";
    }
    @PostMapping("/")
    public String loginUser(@Validated Users user) {
        userService.checkIf(user);
        return "redirect:/home";

    }
}

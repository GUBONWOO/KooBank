package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public void createUser(Users user) {
        userRepository.save(user);
    }

    public Optional<Users> checkIf(Users user) {
        // 사용자 검색 및 비밀번호 체크
        return userRepository.findByName(user.getName())
                .filter(foundUser -> foundUser.getPassword().equals(user.getPassword()));
    }
}




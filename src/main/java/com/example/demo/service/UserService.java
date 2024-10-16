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
    public boolean checkDuplicateUser(String name) {
        return userRepository.findByName(name).isPresent(); // 아이디가 있으면 true 반환
    }

    public void createUser(Users user) {
        userRepository.save(user);
    }

    public Optional<Users> checkIf(Users user) {
        return userRepository.findByName(user.getName())
                .filter(foundUser -> foundUser.getPassword().equals(user.getPassword()));
    }
}




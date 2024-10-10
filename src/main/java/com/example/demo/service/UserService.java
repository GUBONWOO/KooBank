package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public void createUser(Users user) {
        userRepository.save(user);
    }

    public boolean checkIf(Users user) {
               userRepository.findByName(user.getName());
        if(user != null && user.getPassword().equals(user.getPassword())){
            return true;
        }
        return false;
    }
}




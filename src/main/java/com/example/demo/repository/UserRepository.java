package com.example.demo.repository;

import com.example.demo.entity.Users;
import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByName(String name);
}

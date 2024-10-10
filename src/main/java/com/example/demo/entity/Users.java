package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(length = 10)
    private String name;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(length = 2)
    private String gender;

    @NotNull
    @Digits(integer = 10,fraction = 1)
    @Column(length = 10)
    private int phoneNumber;

    @NotNull
    @Size(min = 8, max = 20)
    @Column(length = 20)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    // Getters and Setters
}

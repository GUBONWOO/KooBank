package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;



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
    @Column(length = 10,unique = true)
    private String name;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(length = 2)
    private String gender;

    @NotNull
    @Size( min= 1,max=8)
    @Column(length = 10)
    private String phoneNumber;

    @NotNull
    @Size(min = 8, max = 20 ,message = "비밀번호를 8~20 자")
    @Column(length = 20)
    private String password;




}

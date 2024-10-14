package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @NotNull
    @Digits(integer =10 ,fraction = 1)
    @Column(length = 10)
    private String number;

    @NotNull
    @Digits(integer =32 ,fraction = 1)
    @Column(length = 32)
    private Long balance;

    @NotNull
    @Digits(integer =1 ,fraction = 1)
    @Column(length = 1)
    private int status;

    private String accountDate;


    private int sequence ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)  //
//    private List<History> histories;

}
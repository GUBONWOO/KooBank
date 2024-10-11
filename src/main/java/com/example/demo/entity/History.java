package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
@Data
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max =1)
    @Column(length =1)
    private int status;

    @NotNull
    @Size(min =10, max =10)
    @Column(length = 10)
    private String recipient_account;

    @NotNull
    @Digits(integer =15 ,fraction = 1)
    @Column(length = 15)
    private  Long transaction_amount;


    @ManyToOne
    @JoinColumn(name="account_number", referencedColumnName = "number")
    private Account account;

}

package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
@Data
@Entity
public class Account {

    @Id
    @NotNull
    @Size(min =10, max = 10)
    @Column(length = 10)
    private String number;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(length = 32)
    private Long balance;

    @NotNull
    @Size(max = 1)
    @Column(length = 1)
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)  //
    private List<History> histories;

}
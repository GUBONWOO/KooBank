package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber(String number);

    List<Account> findAllByUserId(int userId);

    @Query(value = "SELECT MAX(sequence) FROM account", nativeQuery = true)
    Integer getSequenceSQL();

    void deleteByNumber(String number);
}

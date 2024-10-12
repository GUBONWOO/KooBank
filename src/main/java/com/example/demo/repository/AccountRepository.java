package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // 계좌 번호로 계좌 조회
    Optional<Account> findByNumber(String number);

    // 동일한 계좌 번호(날짜 기반)로 계좌 리스트 조회
    List<Account> findAllByUserId(int userId);

    // 계좌 번호를 날짜 기반으로 조회 (계좌 날짜별 조회)
    List<Account> findAllByAccountDate(String accountDate);  // accountDate 필드 기준으로 조회
//
    @Query(value = "SELECT MAX(sequence) FROM account", nativeQuery = true)
    Integer getSequenceSQL();

    void deleteByNumber(String number);
}

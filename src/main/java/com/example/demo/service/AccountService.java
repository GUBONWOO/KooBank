package com.example.demo.service;
import com.example.demo.entity.Account;
import com.example.demo.entity.Users;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // 모든 계좌 조회 (number 기반)
    public List<Account> getAccountsAll(int number) {
        return accountRepository.findAllByNumber(String.valueOf(number));
    }

    // 계좌 번호 생성 메서드
    public String generateAccountNumber() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date()); // 현재 날짜를 YYYYMMDD 형식으로 가져옴
        int counter = getAccountCounter(date);  // 중복되지 않는 계좌 번호 생성

        return date + String.format("%02d", counter);  // 날짜 + 2자리 카운터 형식
    }

    // 계좌 저장 메서드
    public void saveAccount(String accountNumber, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        Users loggedInUser = getLoggedInUser(session);

        if (loggedInUser != null) {


            Account account = new Account();
            account.setNumber(accountNumber);
            account.setBalance(0L);  // 기본 잔액 설정
            account.setStatus(1);  // 계좌 상태 기본값 설정 (예: 활성 상태)

            // 로그인한 사용자와 계좌 연결
            account.setUser(loggedInUser);

            // 계좌 저장
            accountRepository.save(account);
        } else {
            throw new RuntimeException("로그인된 사용자를 찾을 수 없습니다.");
        }
    }

    // 중복 계좌번호가 존재하는지 확인하는 메서드
    private boolean accountCheck(String accountNumber) {
        return accountRepository.findByNumber(accountNumber).isPresent();
    }

    // 세션에서 로그인한 사용자 정보를 가져오는 메서드
    private Users getLoggedInUser(HttpSession session) {
        return (Users) session.getAttribute("loggedInUser");
    }

    // 해당 날짜로 계좌 번호 중복 여부 확인 후 다음 번호 생성
    private int getAccountCounter(String date) {
        List<Account> accounts = accountRepository.findAllByNumber(date);  // 현재 날짜로 계좌 조회
        return accounts.size() + 1;  // 이미 생성된 계좌의 개수 + 1 (다음 계좌 번호)
    }
}

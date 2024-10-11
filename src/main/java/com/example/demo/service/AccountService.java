package com.example.demo.service;
import com.example.demo.entity.Account;
import com.example.demo.entity.Users;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<Account> getAccountsAll (String number){
        return accountRepository.findAllByNumber(number);
    }

    // 계좌번호 생성 메서드
    public String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;

        // 중복되지 않는 계좌번호가 나올 때까지 반복
        do {
            StringBuilder accountNumberBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                accountNumberBuilder.append(random.nextInt(10));
            }
            accountNumber = accountNumberBuilder.toString();
        } while (accountCheck(accountNumber)); // 중복 여부 검사

        return accountNumber;
    }

    // 계좌 저장 메서드
    public void saveAccount(String accountNumber, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        Users loggedInUser = getLoggedInUser(session);

        if (loggedInUser != null) {
            // 계좌 객체 생성 및 사용자 연결
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
}

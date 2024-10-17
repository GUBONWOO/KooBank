package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Users;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final EmailService emailService;
    private final YourService yourService;

    // 모든 계좌 조회 (number 기반)
    public List<Account> getAccountsAll(int userId) {
        return accountRepository.findAllByUserId(userId);
    }

    //     계좌 번호 생성 메서드
    public String generateAccountNumber(String accountDate, int sequence) {
        return accountDate + String.format("%02d", sequence);

    }

    //
    // 계좌 저장 메서드
    public void saveAccount(HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        Users loggedInUser = getLoggedInUser(session);

        if (loggedInUser != null) {

            Account account = new Account();

            String accountDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

            int newSequence = accountRepository.getSequenceSQL() + 1;
//
            String accountNumber = generateAccountNumber(accountDate, newSequence);

            account.setNumber(accountNumber);
            account.setBalance(0L);
            account.setStatus(1);
            account.setUser(loggedInUser);
            account.setAccountDate(accountDate);

            account.setSequence(newSequence);

            accountRepository.save(account);
        } else {
            throw new RuntimeException("로그인된 사용자를 찾을 수 없습니다.");
        }
    }

    private Users getLoggedInUser(HttpSession session) {
        return (Users) session.getAttribute("loggedInUser");
    }

    @Transactional
    public void deleteAccount(String accountNumber) {
        accountRepository.deleteByNumber(accountNumber);
    }

    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    ;

    public void deposit(String number, long amount) {
        Account account = accountRepository.findByNumber(number);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            historyService.logTransactionHistory(account, number, amount, 1);
        }



    }
    public void withdraw(String number, long amount) {
        Account account = accountRepository.findByNumber(number);
        if (account != null && amount <= account.getBalance()) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            historyService.logTransactionHistory(account, number, amount, 2);
        }
    }
    @Transactional
    public  Account transaction(String number, long amount,String transactionNumber) {

        Account account = accountRepository.findByNumber(number);
        if (account != null && amount <= account.getBalance()) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);

        }     historyService.logTransactionHistory(account, transactionNumber, amount, 3);

        Account transaction = accountRepository.findByNumber(transactionNumber);
        if (transaction != null) {
            transaction.setBalance(transaction.getBalance() + amount);
            accountRepository.save(transaction);

        }
        historyService.logTransactionHistory(transaction, number, amount, 4);
        yourService.sendSampleEmail();
//        emailService.sendEmail(recipientEmail, subject, body);
        return account;
    }

}
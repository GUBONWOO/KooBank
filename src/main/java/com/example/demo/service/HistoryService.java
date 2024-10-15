package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.History;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;

    public List<History> logTransactionHistory(Account account, String recipientAccount, Long amount, int status) {
        History history = new History();
        history.setAccount(account);
        history.setRecipient_account(recipientAccount);
        history.setTransaction_amount(amount);
        history.setStatus(status);

        historyRepository.save(history);
        return null;
    }
    public List<History> findByAccount(Account account) {
        return historyRepository.findByAccount(account);
    }
}

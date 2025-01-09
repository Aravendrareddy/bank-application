package com.example.banking.service;

import com.example.banking.model.Transaction;
import com.example.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public Transaction createTransaction(UUID accountId, Transaction.TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(accountService.getAccount(accountId));
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setReferenceNumber(generateReferenceNumber());

        accountService.updateBalance(accountId, 
            type == Transaction.TransactionType.WITHDRAWAL ? amount.negate() : amount);

        return transactionRepository.save(transaction);
    }

    private String generateReferenceNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
package com.example.banking.controller;

import com.example.banking.model.Transaction;
import com.example.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Transaction> deposit(
            @PathVariable UUID accountId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(
            transactionService.createTransaction(accountId, Transaction.TransactionType.DEPOSIT, amount));
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable UUID accountId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(
            transactionService.createTransaction(accountId, Transaction.TransactionType.WITHDRAWAL, amount));
    }
}
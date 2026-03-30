package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.entity.Transaction;
import com.bank.entity.TransactionStatus;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Transaction transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount, String description) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        // Проверяем активность счетов
        if (!fromAccount.getIsActive() || !toAccount.getIsActive()) {
            throw new RuntimeException("One or both accounts are inactive");
        }

        // Проверяем достаточно ли средств для дебетовых счетов
        if (fromAccount.getAccountType() == AccountType.DEBIT &&
                fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds in account: " + fromAccount.getAccountNumber());
        }

        // Создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setDescription(description);

        try {
            // Выполняем перевод
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            transaction.setStatus(TransactionStatus.SUCCESS);
            return transactionRepository.save(transaction);

        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException("Transaction failed: " + e.getMessage());
        }
    }

    public java.util.List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    public java.util.List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
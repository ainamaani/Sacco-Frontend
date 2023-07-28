package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Transactions;

public interface TransactionService {
    void makeTransaction(String transactionType, double amount, String userEmail);

    List<Transactions> getTransactionsByUserEmail(String userEmail);

    double getAccountBalance(String userEmail);

    public void updateAccountBalance(String userEmail, double newBalance);
}

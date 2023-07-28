package org.pahappa.systems.kimanyisacco.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pahappa.systems.kimanyisacco.daos.TransactionDAO;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;
    private Map<String, Double> userAccountBalanceMap = new HashMap<>();

    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public void makeTransaction(String transactionType, double amount, String userEmail) {
        // Create a new Transaction instance
        Transactions transaction = new Transactions();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setSaccoMember(userEmail);

        // Save the transaction to the repository
        transactionDAO.save(transaction);
    }

    @Override
    public List<Transactions> getTransactionsByUserEmail(String userEmail) {
        return transactionDAO.getTransactionsByUserEmail(userEmail);
    }

    @Override
    public double getAccountBalance(String userEmail) {
        // Check if the user's balance is already cached in the map
        Double cachedBalance = userAccountBalanceMap.get(userEmail);
        if (cachedBalance != null) {
            return cachedBalance;
        } else {
            // Fetch the balance from the database and update the cache
            double balance = transactionDAO.getAccountBalanceByEmail(userEmail);
            userAccountBalanceMap.put(userEmail, balance);
            return balance;
        }
    }

    @Override
    public void updateAccountBalance(String userEmail, double newBalance) {
        // Update the cached balance for the user
        userAccountBalanceMap.put(userEmail, newBalance);
    }
}

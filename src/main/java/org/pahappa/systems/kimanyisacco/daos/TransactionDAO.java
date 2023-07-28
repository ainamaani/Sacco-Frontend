package org.pahappa.systems.kimanyisacco.daos;

import java.util.List;

import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public interface TransactionDAO {
    void save(Transactions transaction);

    void setSessionFactory(SessionFactory sessionFactory);

    List<Transactions> getTransactionsByUserEmail(String userEmail);

    double getAccountBalanceByEmail(String userEmail);
}

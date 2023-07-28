package org.pahappa.systems.kimanyisacco.daos.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.pahappa.systems.kimanyisacco.daos.TransactionDAO;
import org.pahappa.systems.kimanyisacco.models.Transactions;



public class TransactionDAOImpl implements TransactionDAO {

    private SessionFactory sessionFactory;

    public TransactionDAOImpl() {
        // Initialize the SessionFactory here (you can load the configuration from hibernate.cfg.xml)
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

  

    




    @Override
    public void save(Transactions transaction) {
        try (Session session = sessionFactory.openSession()) {
            Transaction hibernateTransaction = session.beginTransaction();
            try {
                session.save(transaction);
                hibernateTransaction.commit();
            } catch (Exception e) {
                if (hibernateTransaction != null) {
                    hibernateTransaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }
    

    @Override
    public List<Transactions> getTransactionsByUserEmail(String userEmail) {
        Session session = sessionFactory.openSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Transactions> criteriaQuery = cb.createQuery(Transactions.class);
            Root<Transactions> root = criteriaQuery.from(Transactions.class);
            criteriaQuery.select(root).where(cb.equal(root.get("saccoMember"), userEmail));
            return session.createQuery(criteriaQuery).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
public double getAccountBalanceByEmail(String userEmail) {
    Session session = sessionFactory.openSession();
    try {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteriaQuery = cb.createQuery(Double.class);
        Root<Transactions> root = criteriaQuery.from(Transactions.class);
        criteriaQuery.select(cb.sum(root.get("amount"))).where(cb.equal(root.get("saccoMember"), userEmail));
        Double result = session.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : 0.0; 
    } catch (NoResultException e) {
        return 0.0;
    } finally {
        session.close();
    }
}

}

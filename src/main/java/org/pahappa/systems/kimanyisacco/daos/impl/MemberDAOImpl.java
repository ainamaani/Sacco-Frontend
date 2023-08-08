package org.pahappa.systems.kimanyisacco.daos.impl;



import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;


public class MemberDAOImpl implements MemberDAO {
    private SessionFactory sessionFactory;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void save(Member member) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //getting the applicants
    @Override
    public List<Member> getAllMembers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Member> members = null;

        try {
            transaction = session.beginTransaction();
            
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Member> criteriaQuery = builder.createQuery(Member.class);
            Root<Member> root = criteriaQuery.from(Member.class);
            criteriaQuery.select(root);
            
            Query<Member> query = session.createQuery(criteriaQuery);
            members = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return members;
    }

    @Override
public void delete(Member member) {
    Session session = null;
    Transaction transaction = null;
    
    try {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(member);
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        if (session != null) {
            session.close();
        }
    }
}   

    @Override
    public Member getMemberByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Member WHERE email = :email", Member.class)
                        .setParameter("email", email)
                        .uniqueResult();
        }
    }

    @Override
    public void update(Member member) {
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Member> getMembersByStatus(String status) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Member> members = null;

        try {
            transaction = session.beginTransaction();
            
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Member> criteriaQuery = builder.createQuery(Member.class);
            Root<Member> root = criteriaQuery.from(Member.class);
            criteriaQuery.select(root).where(builder.equal(root.get("membershipStatus"), status));
            
            Query<Member> query = session.createQuery(criteriaQuery);
            members = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return members;
    }


    @Override
    public List<Transactions> getAllTransactionsMade() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Transactions> transactions = null;

        try {
            transaction = session.beginTransaction();
            
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Transactions> criteriaQuery = builder.createQuery(Transactions.class);
            Root<Transactions> root = criteriaQuery.from(Transactions.class);
            criteriaQuery.select(root);
            
            Query<Transactions> query = session.createQuery(criteriaQuery);
            transactions = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return transactions;
    }
}

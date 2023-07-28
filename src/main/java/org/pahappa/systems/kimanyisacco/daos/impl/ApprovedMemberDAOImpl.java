package org.pahappa.systems.kimanyisacco.daos.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.daos.ApprovedMemberDAO;
import org.pahappa.systems.kimanyisacco.models.ApprovedMember;

public class ApprovedMemberDAOImpl implements ApprovedMemberDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(ApprovedMember approvedMember) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(approvedMember);
        session.getTransaction().commit();
    }
}

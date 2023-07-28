package org.pahappa.systems.kimanyisacco.services;

import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.models.ApprovedMember;

public interface ApprovedMemberService {
    void save(ApprovedMember approvedMember);

    void setSessionFactory(SessionFactory sessionFactory);
}

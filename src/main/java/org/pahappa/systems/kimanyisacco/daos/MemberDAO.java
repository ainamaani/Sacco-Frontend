package org.pahappa.systems.kimanyisacco.daos;

import java.util.List;

import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public interface MemberDAO {
    void save(Member member);

    List<Member> getAllMembers();

    void setSessionFactory(SessionFactory sessionFactory);

    void delete(Member member);

    void update(Member member);

    Member getMemberByEmail(String email);

    List<Member> getMembersByStatus(String status);

    List<Transactions> getAllTransactionsMade();

}

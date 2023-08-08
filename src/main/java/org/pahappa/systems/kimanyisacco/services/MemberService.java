package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public interface MemberService {
    void register(Member member);

    List<Member> getAllMembers();

    void setMemberDAO(MemberDAO memberDAO);

    void delete(Member member);

    void approveMember(Member member);

    boolean isEmailExists(String email);

    List<Member> getApprovedMembers();

    List<Transactions> getAllTransactions();
}

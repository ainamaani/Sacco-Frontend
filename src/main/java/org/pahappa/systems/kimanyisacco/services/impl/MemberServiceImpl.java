package org.pahappa.systems.kimanyisacco.services.impl;

import java.util.List;

import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.MemberService;


public class MemberServiceImpl extends TryService implements MemberService {
    private MemberDAO memberDAO;

    
    @Override
    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public void register(Member member) {
        memberDAO.save(member);
    }

    @Override
    void registerA(Member member) {
        memberDAO.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    @Override
    public void delete(Member member) {
        memberDAO.delete(member);
    }

    @Override
    public void approveMember(Member member) {
        member.setMembershipStatus("Approved");
        memberDAO.update(member); 
    }

    @Override
    public List<Member> getApprovedMembers() {
        return memberDAO.getMembersByStatus("Approved");
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return memberDAO.getAllTransactionsMade();
    }

    @Override
    public boolean isEmailExists(String email) {
    Member existingMember = memberDAO.getMemberByEmail(email);
    return existingMember != null;
}

    


}

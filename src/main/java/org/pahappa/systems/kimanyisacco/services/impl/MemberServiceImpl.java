package org.pahappa.systems.kimanyisacco.services.impl;

import java.util.List;

import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO;
    private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    
    @Override
    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public void register(Member member) {
        memberDAO.save(member);
        logger.info("Member registered successfully: {}", member.getFullName());
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
    public boolean isEmailExists(String email) {
    Member existingMember = memberDAO.getMemberByEmail(email);
    return existingMember != null;
}


}

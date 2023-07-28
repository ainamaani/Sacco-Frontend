package org.pahappa.systems.kimanyisacco.services.impl;

import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.daos.ApprovedMemberDAO;
import org.pahappa.systems.kimanyisacco.models.ApprovedMember;
import org.pahappa.systems.kimanyisacco.services.ApprovedMemberService;

public class ApprovedMemberServiceImpl implements ApprovedMemberService {
    private ApprovedMemberDAO approvedMemberDAO;
    

    // public void setApprovedMemberDAO(ApprovedMemberDAO approvedMemberDAO) {
    //     this.approvedMemberDAO = approvedMemberDAO;
    // }

    @Override
    public void save(ApprovedMember approvedMember) {
        approvedMemberDAO.save(approvedMember);
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
    }
}

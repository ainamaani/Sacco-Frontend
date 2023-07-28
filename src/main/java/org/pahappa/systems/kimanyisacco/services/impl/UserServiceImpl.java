package org.pahappa.systems.kimanyisacco.services.impl;

import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.UserService;

public class UserServiceImpl implements UserService {
    private final MemberDAO memberDAO;

    public UserServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public boolean checkUserCredentials(String email, String password) {
        Member user = memberDAO.getMemberByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public String getMembershipStatusByEmail(String email) {
        Member user = memberDAO.getMemberByEmail(email);
        return (user != null) ? user.getMembershipStatus() : null;
    }

    @Override
    public String getPasswordByEmail(String email) {
        Member user = memberDAO.getMemberByEmail(email);
        return (user != null) ? user.getPassword() : null;
    }
}

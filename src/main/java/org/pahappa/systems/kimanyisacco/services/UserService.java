package org.pahappa.systems.kimanyisacco.services;

public interface UserService {
    boolean checkUserCredentials(String email, String password);

    String getMembershipStatusByEmail(String email);

    String getPasswordByEmail(String email);
}

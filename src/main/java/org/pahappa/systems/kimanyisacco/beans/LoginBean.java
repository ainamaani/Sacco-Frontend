package org.pahappa.systems.kimanyisacco.beans;

import org.mindrot.jbcrypt.BCrypt;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.daos.impl.MemberDAOImpl;
import org.pahappa.systems.kimanyisacco.services.UserService;
import org.pahappa.systems.kimanyisacco.services.impl.UserServiceImpl;

@ManagedBean(name = "loginBeann")
@SessionScoped
public class LoginBean {
    private String email;
    private String password;
    private final UserService userService;

    public LoginBean() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        MemberDAO memberDAO = new MemberDAOImpl();
        this.userService = new UserServiceImpl(memberDAO);
        memberDAO.setSessionFactory(sessionFactory);
    }

    public String login() {
        String membershipStatus = userService.getMembershipStatusByEmail(email);

        if (email != null && email.startsWith("adminkimwanyis")) {
            // Set the logged-in user's email in the session for later use
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUserEmail", email);
            email = "";
            password = "";
            return "Applicants?faces-redirect=true";
        }
    
        if (membershipStatus != null && membershipStatus.equals("Approved")) {
            // The membership is approved, proceed with the regular login check
            String hashedPassword = userService.getPasswordByEmail(email);
    
            if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
                // Passwords match, login successful
                // Set the logged-in user's email in the session for later use
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUserEmail", email);
                email = "";
                password = "";
                return "Dashboard?faces-redirect=true";
            } else {
                // Show an error message if login fails
                FacesContext.getCurrentInstance().addMessage("loginButton",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
                return null;
            }
    
        } else if (membershipStatus != null && !membershipStatus.equals("Approved")) {
            FacesContext.getCurrentInstance().addMessage("loginButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Your membership is not approved yet", null));
            return null;
        } else {
            // Show an error message if the email is not found in the database
            FacesContext.getCurrentInstance().addMessage("loginButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
            return null;
        }
    }

    public String logout() {
        // Invalidate the session and redirect to the login page
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package org.pahappa.systems.kimanyisacco.beans;

import org.mindrot.jbcrypt.BCrypt;

import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pahappa.systems.kimanyisacco.daos.MemberDAO;
import org.pahappa.systems.kimanyisacco.daos.impl.MemberDAOImpl;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

@ManagedBean(name = "memberBean")
@ViewScoped
public class MemberBean {
    private Member member;
    private List<Member> members;
    private List<Member> approvedMembers;
    private List<Transactions> transactionsMade;
    private MemberService memberService;

    public MemberBean() {
        member = new Member();
        memberService = new MemberServiceImpl();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        MemberDAO memberDAO = new MemberDAOImpl();
        memberService.setMemberDAO(memberDAO);
        memberDAO.setSessionFactory(sessionFactory);
        members = memberService.getAllMembers();
        approvedMembers = memberService.getApprovedMembers();
        transactionsMade = memberService.getAllTransactions();
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Transactions> getTransactionsMade() {
        return transactionsMade;
    }

    public void setTransactionsMade(List<Transactions> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }

    public void register() {
        // First, check if any required fields are empty
        if (!areFieldsFilled()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please fill in all the required fields."));
            return; // Abort the registration process
        }
        // Check if the email already exists in the database
        if (memberService.isEmailExists(member.getEmail())) {
            // Display a growl message indicating that the email is already taken
            PrimeFaces.current().executeScript("PF('emailExists').show()");
            return; // Abort the registration process
        }

        // Hash the password using bcrypt
        String hashedPassword = BCrypt.hashpw(member.getPassword(), BCrypt.gensalt());
        member.setPassword(hashedPassword);

        memberService.register(member);
        // Reset the member object or perform any other necessary actions after
        // registration
        member = new Member();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect("Login.xhtml");
        } catch (IOException e) {
            // Handle the exception if redirecting fails
            e.printStackTrace();
        }
    }

    private boolean areFieldsFilled() {
        return member.getFullName() != null &&
                !member.getFullName().isEmpty() &&
                member.getGender() != null &&
                member.getDateOfBirth() != null &&
                member.getNationality() != null &&
                member.getResidentialAddress() != null &&
                !member.getResidentialAddress().isEmpty() &&
                member.getEmail() != null &&
                !member.getEmail().isEmpty() &&
                member.getPhoneNumber() != null &&
                !member.getPhoneNumber().isEmpty() &&
                member.getPassword() != null &&
                !member.getPassword().isEmpty() &&
                member.getMembershipStatus() != null&&
                member.getCurrentOccupation() != null &&
                !member.getCurrentOccupation().isEmpty();
    }

    public void approveMember(Member member) {
        // Set the status to "Approved"
        member.setMembershipStatus("Approved");
        // Save the updated member to the database
        memberService.approveMember(member);

        // Send an email notification to the approved member
        sendApprovalEmail(member.getEmail(), member.getFullName());

        FacesContext.getCurrentInstance().addMessage("approveMember",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Member Approved",
                        "The member has been approved successfully."));
    }

    public List<Member> getApprovedMembers() {
        return approvedMembers;
    }

    // send email
    private void sendApprovalEmail(String recipientEmail, String recipientName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aina.isaac2002@gmail.com", "mpzkxekvfqfzijcn");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aina.isaac2002@gmail.com", "Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Membership Approval");
            message.setText("Dear " + recipientName
                    + ",\n\nYour membership has been approved. You can now log in to the system.");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception if there's an issue with the encoding
        }
    }

    public void declineMember(Member member) {
        // First, send an email notification to the member
        sendDeclineEmail(member.getEmail(), member.getFullName());

        // Then, delete the member from the database
        memberService.delete(member);
        // Refresh the page to reflect the updated list of members
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            // Show a growl message indicating that the member has been declined
            FacesContext.getCurrentInstance().addMessage("declineMember",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Member Declined", "The member has been declined."));
            externalContext.redirect(externalContext.getRequestContextPath() + "/pages/Applicants.xhtml");
        } catch (IOException e) {
            // Handle the exception if redirecting fails
            e.printStackTrace();
        }
    }

    private void sendDeclineEmail(String recipientEmail, String recipientName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aina.isaac2002@gmail.com", "mpzkxekvfqfzijcn");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aina.isaac2002@gmail.com", "Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Membership Approval");
            message.setText("Dear " + recipientName + ",\n\nYour membership has been denied.");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception if there's an issue with the encoding
        }
    }

}

package org.pahappa.systems.kimanyisacco.beans;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pahappa.systems.kimanyisacco.daos.TransactionDAO;
import org.pahappa.systems.kimanyisacco.daos.impl.TransactionDAOImpl;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;

import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean(name = "transactionBean")
@SessionScoped
public class TransactionBean {
    private Transactions transaction;
    private List<Transactions> userTransactions;
    private TransactionDAO transactionDAO;
    private TransactionService transactionService;
    private double accountBalance;
    private String transferEmail;
    private double transferAmount;

    public TransactionBean() {
        transaction = new Transactions();
        transactionDAO = new TransactionDAOImpl();
        new TransactionServiceImpl(transactionDAO);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        transactionDAO.setSessionFactory(sessionFactory);

        transactionService = new TransactionServiceImpl(transactionDAO);
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionBean(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public String getTransferEmail() {
        return transferEmail;
    }
    
    public void setTransferEmail(String transferEmail) {
        this.transferEmail = transferEmail;
    }

    public double getTransferAmount() {
        return transferAmount;
    }
    
    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }
    

    public void makeTransaction() {
        String userEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUserEmail");
    
        if (userEmail == null || userEmail.isEmpty()) {
            // Show an error message if the user is not logged in
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please log in to make a transaction", null));
            return;
        }
    
        // Get the transaction data from the properties
        String transactionType = transaction.getTransactionType();
        double amount = transaction.getAmount();
    
        if ("withdraw".equalsIgnoreCase(transactionType)) {
            // Fetch the account balance from the service and check if the amount is valid for withdrawal
            double currentBalance = transactionService.getAccountBalance(userEmail);
            if (amount > currentBalance) {
                // Display a growl message to inform the user that they can't withdraw more than their balance
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid transaction!", "Withdrawal amount exceeds your account balance!"));
                return;
            }
        }
    
        // Update the account balance based on the transaction type
        double currentBalance = transactionService.getAccountBalance(userEmail);
        if ("deposit".equalsIgnoreCase(transactionType)) {
            currentBalance += amount;
        } else if ("withdraw".equalsIgnoreCase(transactionType)) {
            currentBalance -= amount;
        } else {
            
        }
    
        // Save the updated account balance along with the transaction details
        transaction.setAccountBalance(currentBalance);
    
        // Call the service method to create the transaction
        transactionService.makeTransaction(transactionType, amount, userEmail,currentBalance);
    
        transaction.setTransactionType(null);
        transaction.setAmount(0.0);
    
        // Update the current balance in the bean after the transaction
        // This will update the balance displayed in the template without fetching from the database
        transactionService.updateAccountBalance(userEmail, currentBalance);
    }
    

    public double getAccountBalance() {
        String userEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUserEmail");
        if (userEmail != null && !userEmail.isEmpty()) {
            // Fetch the account balance from the service and update the property
            accountBalance = transactionService.getAccountBalance(userEmail);
        }
        return accountBalance;
    }
    

    public List<Transactions> getUserTransactions() {
        String userEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUserEmail");
        if (userEmail == null || userEmail.isEmpty()) {
            // User is not logged in, return an empty list
            return Collections.emptyList();
        }

        userTransactions = transactionService.getTransactionsByUserEmail(userEmail);
        return userTransactions;
    }


    public void makeInternalTransfer() {
        String userEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUserEmail");
    
        if (userEmail == null || userEmail.isEmpty()) {
            // Show an error message if the user is not logged in
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please log in to make a transaction", null));
            return;
        }
    
        // Check if the recipient email exists and is not the same as the sender
        if (transferEmail == null || transferEmail.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Recipient email cannot be empty", null));
            return;
        } else if (transferEmail.equals(userEmail)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot transfer to your own account", null));
            return;
        }

        
    
        // Fetch the sender's current balance
        double senderBalance = transactionService.getAccountBalance(userEmail);
    
        // Fetch the recipient's current balance
        double recipientBalance = transactionService.getAccountBalance(transferEmail);
    
        double transferAmount = this.transferAmount;

    if (transferAmount <= 0) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transfer amount must be greater than zero", null));
        return;
    }

    // Check if the sender has sufficient balance for the transfer
    if (transferAmount > senderBalance) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient balance for transfer", null));
        return;
    }
    
        // Update the sender's balance
        senderBalance -= transferAmount;

        transaction.setAccountBalance(senderBalance);
    
        // Update the recipient's balance
        recipientBalance += transferAmount;
    
        // Save the updated balances along with the transactions
        transactionService.makeTransaction("internal_transfer_sender", transferAmount, userEmail, senderBalance);
        transactionService.makeTransaction("internal_transfer_recipient", transferAmount, transferEmail, recipientBalance);
    
        // Show a success message
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Internal transfer successful", null));
    
        // Clear the transfer email and amount after successful transfer
        transaction.setAmount(0.0);
        transferEmail = null;
    }
}

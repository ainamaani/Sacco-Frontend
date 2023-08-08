package org.pahappa.systems.kimanyisacco.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.pahappa.systems.kimanyisacco.enums.Gender;

@Entity
@Table(name = "applicants")

public class Member {
    private int id;
    private String fullName;
    private Gender gender;
    private Date dateOfBirth;
    private String nationality;
    private String residentialAddress;
    private String email;
    private String phoneNumber;
    private String employmentStatus;
    private String currentOccupation;
    private String employerName;
    private String employerEmail;
    private String employerPhoneNumber;
    private String nextOfKin;
    private String nextOfKinEmail;
    private String nextOfKinPhoneNumber;
    private String membershipStatus;
    private String password;

    // Getters and setters for all the fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "fullname", nullable = false, length = 255)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "dateOfBirth", nullable = false, length = 255)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Column(name = "residentialAddress", nullable = false, length = 255)
    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phoneNumber", nullable = false, length = 255)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "employmentStatus", nullable = false, length = 255)
    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @Column(name = "currentOccupation", nullable = false, length = 255)
    public String getCurrentOccupation() {
        return currentOccupation;
    }

    public void setCurrentOccupation(String currentOccupation) {
        this.currentOccupation = currentOccupation;
    }

    @Column(name = "employerName", nullable = false, length = 255)
    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @Column(name = "employerEmail", nullable = false, length = 255)
    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    @Column(name = "employerPhoneNumber", nullable = false, length = 255)
    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    @Column(name = "nextOfKin", nullable = false, length = 255)
    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Column(name = "nextOfKinEmail", nullable = false, length = 255)
    public String getNextOfKinEmail() {
        return nextOfKinEmail;
    }

    public void setNextOfKinEmail(String nextOfKinEmail) {
        this.nextOfKinEmail = nextOfKinEmail;
    }

    @Column(name = "nextOfKinPhoneNumber", nullable = false, length = 255)
    public String getNextOfKinPhoneNumber() {
        return nextOfKinPhoneNumber;
    }

    public void setNextOfKinPhoneNumber(String nextOfKinPhoneNumber) {
        this.nextOfKinPhoneNumber = nextOfKinPhoneNumber;
    }

    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "membershipstatus", nullable = false, length = 255)
    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }
}

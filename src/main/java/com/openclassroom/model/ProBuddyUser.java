package com.openclassroom.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class ProBuddyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Integer age;
    private String phone;
    private String nationalID;
    private Integer id;
    private String password;
    private String accountNo;
    private List<ProBuddyRole> proBuddyRoles;

    protected ProBuddyUser() {
    }


    public ProBuddyUser(String firstName, String lastName, String address,
                        String email, Integer age, String phone, String nationalID,
                        Integer id, String password, String accountNo, List<ProBuddyRole> proBuddyRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.nationalID = nationalID;
        this.id = id;
        this.password = password;
        this.accountNo = accountNo;
        this.proBuddyRoles = proBuddyRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", nationalID='" + nationalID + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", roles='" + proBuddyRoles + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalIdNo) {
        this.nationalID = nationalIdNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<ProBuddyRole> getRoles() {return proBuddyRoles;}

    public void setRoles(List<ProBuddyRole> proBuddyRoles) {this.proBuddyRoles = proBuddyRoles;}
}

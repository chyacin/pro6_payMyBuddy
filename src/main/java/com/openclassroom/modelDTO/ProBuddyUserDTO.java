package com.openclassroom.modelDTO;
import javax.validation.constraints.NotBlank;

import com.openclassroom.model.ProBuddyUser;


public class ProBuddyUserDTO {

    @NotBlank(message="Please enter your First Name")
    private String firstName;
    @NotBlank(message="Please enter your Last Name")
    private String lastName;
    @NotBlank(message="Please enter your address")
    private String address;
    @NotBlank(message="Please enter your email address")
    private String email;
    @NotBlank(message="Please enter your age")
    private int age;
    @NotBlank(message="Please enter your phone")
    private String phone;
    @NotBlank(message="Please enter your ID Card number")
    private String nationalID;
    @NotBlank(message="Please create a password")
    private String password;
    @NotBlank(message="Please enter your Bank Name")
    private String bankName;
    @NotBlank(message="Please enter your Bank account number")
    private String bankAccountNumber;

    public ProBuddyUserDTO(String firstName, String lastName, String address, String email, int age, String phone,
                           String nationalID, String password, String bankName, String bankAccountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.nationalID = nationalID;
        this.password = password;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }

    public ProBuddyUserDTO() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public ProBuddyUser createProBuddyUser() {
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setFirstName(firstName);
        proBuddyUser.setLastName(lastName);
        proBuddyUser.setEmail(email);
        proBuddyUser.setAddress(address);
        proBuddyUser.setPassword(password);
        proBuddyUser.setAge(age);
        proBuddyUser.setPhone(phone);
        proBuddyUser.setNationalID(nationalID);
        proBuddyUser.setEnabled(false);

        return proBuddyUser;
    }
}

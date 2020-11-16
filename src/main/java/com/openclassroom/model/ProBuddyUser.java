package com.openclassroom.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class ProBuddyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Please enter your First Name")
    private String firstName;
    @NotBlank(message="Please enter your Last Name")
    private String lastName;
    @NotBlank(message="Please enter your address")
    private String address;
    @NotBlank(message="Please enter your email address")
    private String email;
    
    @Min(message = "must be older or equal to 18", value = 0L)
    @NotNull(message="Please enter your age")
    private int age;
    @NotBlank(message="Please enter your phone")
    private String phone;
    @NotBlank(message="Please enter your ID Card number")
    private String nationalID;
    @NotBlank(message="Please create a password")
    private String password;
    private boolean enabled;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private ProBuddyAccount account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pro_user_role" ,
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<ProBuddyRole> role;

    public ProBuddyUser() {
    }

    public ProBuddyUser(String firstName, String lastName, String address, String email, int age,
                        String phone, String nationalID, String password, Set<ProBuddyRole> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.nationalID = nationalID;
        this.password = password;
        this.account = null;
        this.role = role;
    }



    @Override
    public String toString() {
        return "ProBuddyUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", nationalID='" + nationalID + '\'' +
                ", password='" + password + '\'' +
                ", accountNo=" + account.getId() +
                ", roles=" + role +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ProBuddyAccount getAccount() {
        return account;
    }

    public void setAccount(ProBuddyAccount account) {
        this.account = account;
    }

    public Set<ProBuddyRole> getRoles() {
        return role;
    }

    public void setRoles(Set<ProBuddyRole> role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

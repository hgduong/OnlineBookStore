/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TungPham
 */
import java.util.Date;
import java.util.List;

public class Account {

    private int accountId;
    private String username;
    private String password;
    private int roleId;
    private String fullname;
    private String phone;
    private Date dob;
    private String gender;
    private Address address;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private String status;

    private List<Address> Addresses;
    private Role role;
    // Constructor
    public Account() {
    }

    public Account(int accountId, String username, String password, int roleId, String fullname, String phone, Date dob, String gender, Address address, String email, Date createdAt, Date updatedAt, String status) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.fullname = fullname;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }


    
    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Address> getAddresses() {
        return Addresses;
    }

    public void setAddresses(List<Address> Addresses) {
        this.Addresses = Addresses;
    }
    
    
}

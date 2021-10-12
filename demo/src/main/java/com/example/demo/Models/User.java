package com.example.demo.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String fullName;

    @Column
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    private boolean isVerified;

    private boolean isRegister;

    public String getFullName() {
        return fullName;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public int getId() {
        return id;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {
    }

}

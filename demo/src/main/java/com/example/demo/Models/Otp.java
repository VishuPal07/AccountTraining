package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int otp;

    private boolean isDeleted;

    @ManyToOne
    private User user;

    public Otp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.example.demo.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String token;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private Date createdOn;

    private Date deletedOn;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    public AuthenticationToken(User user, String token) {
        this.user=user;
        this.token=token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public AuthenticationToken(String token) {
        this.token = token;
    }

    public AuthenticationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public AuthenticationToken() {
    }

    public boolean isExpired(AuthenticationToken authenticationToken) {
        boolean token = authenticationToken.isDeleted();
        System.out.println(token);
        if(!token)
        return true;
        else
            return false;
    }
}

package com.service;

import com.model.User;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() { return currentUser; }
    public void setUser(User user) { this.currentUser = user; }

    public void cleanUserSession() {
        currentUser = null;
    }
}
package com.example.demo.model;


public abstract class User {
    private final UserRole role;

    protected User(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public abstract String getRoleDescription();
}

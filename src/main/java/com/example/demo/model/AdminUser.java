package com.example.demo.model;

/** Администратор приложения. */
public class AdminUser extends User {
    public AdminUser() {
        super(UserRole.ADMIN);
    }

    @Override
    public String getRoleDescription() {
        return "Administrator";
    }
}

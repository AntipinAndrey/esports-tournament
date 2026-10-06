package com.example.demo.model;


public class Player extends User {
    private final String nickname;
    private final String firstName;
    private final String lastName;
    private final String email;

    public Player(
            String nickname,
            String firstName,
            String lastName,
            String email
    ) {
        super(UserRole.PLAYER);
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String getRoleDescription() {
        return "Player";
    }

    public String getNickname() {
        return nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

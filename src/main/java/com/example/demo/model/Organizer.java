package com.example.demo.model;


public class Organizer extends User {
    private final String organizerName;
    private final String email;
    private final String phoneNumber;
    private final String country;

    public Organizer(
            String organizerName,
            String email,
            String phoneNumber,
            String country
    ) {
        super(UserRole.ORGANIZER);
        this.organizerName = organizerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    @Override
    public String getRoleDescription() {
        return "Organizer";
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "organizerName='" + organizerName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

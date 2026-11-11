package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/* Организатор турниров. Название используется как естественный ключ. */
@Entity
@Table(name = "organizers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Organizer extends User {
    @Id
    @jakarta.persistence.Column(length = 100)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String organizerName;

    @jakarta.persistence.Column(nullable = false, unique = true, length = 255)
    @ToString.Include
    private String email;

    @jakarta.persistence.Column(nullable = false, length = 50)
    @ToString.Include
    private String phoneNumber;

    @jakarta.persistence.Column(nullable = false, length = 100)
    @ToString.Include
    private String country;

    @OneToMany(mappedBy = "organizer")
    private List<Tournament> tournaments = new ArrayList<>();

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
}

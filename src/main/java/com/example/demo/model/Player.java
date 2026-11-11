package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/* Игрок команды. Nickname используется как естественный ключ. */
@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Player extends User {
    @Id
    @jakarta.persistence.Column(length = 100)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String nickname;

    @jakarta.persistence.Column(nullable = false, length = 100)
    @ToString.Include
    private String firstName;

    @jakarta.persistence.Column(nullable = false, length = 100)
    @ToString.Include
    private String lastName;

    @jakarta.persistence.Column(nullable = false, unique = true, length = 255)
    @ToString.Include
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

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
}

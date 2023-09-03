package com.bookstore.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;
    private String username;
    private String passwordHash;
    private String lastLogin;
    private String isAdmin;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

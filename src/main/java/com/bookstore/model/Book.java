package com.bookstore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String title;
    private String author;
    private BigDecimal price;
    private String releaseYear;
    private Instant createdAt;
    private Instant updatedAt;

    public Book() {
    }

    public Book(String title, String author, BigDecimal price, String releaseYear, Instant createdAt, Instant updatedAt) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.releaseYear = releaseYear;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

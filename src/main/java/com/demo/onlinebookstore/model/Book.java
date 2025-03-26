package com.demo.onlinebookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    // ✅ Default constructor (Required for JPA)
    public Book() {}

    // ✅ Constructor without ID (Used when creating a new book)
    public Book(String title, String author, BigDecimal price, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishedDate = publishedDate;
    }

    // ✅ Constructor with ID (Useful for testing and DTO mapping)
    public Book(Long id, String title, String author, BigDecimal price, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishedDate = publishedDate;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    // ✅ Override toString() for debugging
    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + 
               ", price=" + price + ", publishedDate=" + publishedDate + "]";
    }
}

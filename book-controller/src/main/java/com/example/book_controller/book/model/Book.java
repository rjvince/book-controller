package com.example.book_controller.book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Book class that models books in a library
 */
@Data
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotBlank(message = "Book title cannot be blank")
    protected String title;
    @NotBlank(message = "Book author cannot be blank")
    protected String author;
    @Column(unique = true)
    @NotBlank(message = "Book ISBN cannot be blank")
    protected String isbn;
    @NotNull
    protected LocalDateTime publishedDate;
}

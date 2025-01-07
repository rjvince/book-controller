package com.example.book_controller.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue
    protected Long id;
    @NotBlank(message = "Book title cannot be blank")
    protected String title;
    @NotBlank(message = "Book author cannot be blank")
    protected String author;
    @NotBlank(message = "Book ISBN cannot be blank")
    protected String isbn;
    @NotNull
    protected LocalDateTime publishedDate;
}

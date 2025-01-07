package com.example.book_controller.book.exception;

import com.example.book_controller.book.model.Book;

/**
 * Exception class for when a {@link Book} is not found in the repository
 *
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}

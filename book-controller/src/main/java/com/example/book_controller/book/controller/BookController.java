package com.example.book_controller.book.controller;

import com.example.book_controller.book.data.BookRepository;
import com.example.book_controller.book.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The BookController that exposes endpoints for managing our library
 */
@RestController
@Validated
public class BookController {
    final protected BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Saves a new book
     *
     * @param book incoming book to save
     * @return The newly saved book and HTTP-Created status
     */
    @PostMapping("/books")
    public ResponseEntity<Book> postBook(@RequestBody @Valid Book book) {
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    /**
     * Fetches all existing books
     *
     * @return response containing all books and an HTTP-OK status
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Fetch a particular book by ID
     *
     * @param bookId the ID of the book
     * @return if found: the book and HTTP-OK; otherwise, HTTP-NOT_FOUND
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update a book by ID
     *
     * @param bookId the ID of the book to update
     * @param book   the new book to insert at that ID
     * @return if found: the newly saved book and HTTP-OK; otherwise HTTP-NOT_FOUND
     */
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody @Valid Book book) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book returnedBook = bookOptional.get();
            returnedBook.setTitle(book.getTitle());
            returnedBook.setAuthor(book.getAuthor());
            returnedBook.setIsbn(book.getIsbn());
            returnedBook.setPublishedDate(book.getPublishedDate());
            Book savedBook = bookRepository.save(returnedBook);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a saved book
     *
     * @param bookId the ID to delete
     * @return if found: "Book deleted" and HTTP-NO_CONTENT; otherwise "Book not found" and HTTP-NOT_FOUND
     */
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            bookRepository.delete(bookOptional.get());
            return new ResponseEntity<>("Book deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

// Test endpoint to get the message body format right
//
//    @GetMapping("/dummy")
//    public ResponseEntity<Book> getDummy() {
//        Book b = new Book();
//        b.setIsbn("000-000-000");
//        b.setTitle("Dummy Title");
//        b.setAuthor("Dummy Author");
//        b.setPublishedDate(LocalDateTime.now());
//        return new ResponseEntity<>(b, HttpStatus.OK);
//    }
}

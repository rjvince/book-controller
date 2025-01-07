package com.example.book_controller.book.controller;

import com.example.book_controller.book.data.BookService;
import com.example.book_controller.book.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The BookController that exposes endpoints for managing our library
 */
@RestController
@Validated
public class BookController {
    final protected BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Saves a new book
     *
     * @param book incoming book to save
     * @return The newly saved book and HTTP-Created status
     */
    @PostMapping("/books")
    public ResponseEntity<Book> postBook(@RequestBody @Valid Book book) {
        bookService.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    /**
     * Fetches all existing books
     *
     * @return response containing all books and an HTTP-OK status
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    /**
     * Fetch a particular book by ID
     *
     * @param bookId the ID of the book
     * @return if found: the book and HTTP-OK
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookService.findById(bookId), HttpStatus.OK);
    }

    /**
     * Update a book by ID
     *
     * @param bookId the ID of the book to update
     * @param book   the new book to insert at that ID
     * @return if found: the newly saved book and HTTP-OK
     */
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody @Valid Book book) {
        Book foundBook = bookService.findById(bookId);
        foundBook.setTitle(book.getTitle());
        foundBook.setAuthor(book.getAuthor());
        foundBook.setIsbn(book.getIsbn());
        foundBook.setPublishedDate(book.getPublishedDate());
        Book savedBook = bookService.save(foundBook);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }


    /**
     * Deletes a saved book
     *
     * @param bookId the ID to delete
     * @return if found: "Book deleted" and HTTP-NO_CONTENT
     */
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return new ResponseEntity<>("Book deleted", HttpStatus.NO_CONTENT);
    }

}

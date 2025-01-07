package com.example.book_controller.book.data;

import com.example.book_controller.book.exception.BookNotFoundException;
import com.example.book_controller.book.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service wraps the {@link BookRepository} methods and provide exception support for findById
 */
@Service
public class BookService {
    final protected BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("No Book found with ID: " + bookId));
    }

    public void delete(Long bookId) {
        bookRepository.delete(findById(bookId));
    }
}

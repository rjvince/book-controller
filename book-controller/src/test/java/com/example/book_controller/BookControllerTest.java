package com.example.book_controller;

import com.example.book_controller.book.controller.BookController;
import com.example.book_controller.book.data.BookRepository;
import com.example.book_controller.book.data.BookService;
import com.example.book_controller.book.exception.BookNotFoundException;
import com.example.book_controller.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test cases for {@link BookController}
 *
 * updateBook and deleteBook are omitted in favor of testing in integration;
 * The unit tests would be just a bit too tautological.
 */
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookService);
    }

    @Test
    void testPostBook() {
        LocalDateTime saveTime = LocalDateTime.now();

        Book book = new Book();
        book.setTitle("Beowulf");
        book.setAuthor("Unknown");
        book.setIsbn("1234567890");
        book.setPublishedDate(saveTime);
        ResponseEntity<Book> responseEntity = bookController.postBook(book);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo("Beowulf");
        assertThat(responseEntity.getBody().getAuthor()).isEqualTo("Unknown");
        assertThat(responseEntity.getBody().getIsbn()).isEqualTo("1234567890");
        assertThat(responseEntity.getBody().getPublishedDate()).isEqualTo(saveTime);
    }

    @Test
    void testGetBooks() {
        LocalDateTime saveTime = LocalDateTime.now();

        Book book1 = new Book();
        book1.setTitle("Beowulf");
        book1.setAuthor("Unknown");
        book1.setIsbn("1234567890");
        book1.setPublishedDate(saveTime);

        Book book2 = new Book();
        book2.setTitle("Beowulf 2: Nordic Boogaloo");
        book2.setAuthor("Anonymous");
        book2.setIsbn("0987654321");
        book2.setPublishedDate(saveTime);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        ResponseEntity<List<Book>> responseEntity = bookController.getBooks();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(2);
        assertThat(responseEntity.getBody().get(0)).isEqualTo(book1);
        assertThat(responseEntity.getBody().get(1)).isEqualTo(book2);
    }

    @Test
    void testGetBookById() {
        LocalDateTime saveTime = LocalDateTime.now();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Beowulf");
        book1.setAuthor("Unknown");
        book1.setIsbn("1234567890");
        book1.setPublishedDate(saveTime);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        ResponseEntity<Book> responseEntity = bookController.getBook(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo("Beowulf");
        assertThat(responseEntity.getBody().getAuthor()).isEqualTo("Unknown");
        assertThat(responseEntity.getBody().getIsbn()).isEqualTo("1234567890");
        assertThat(responseEntity.getBody().getPublishedDate()).isEqualTo(saveTime);
    }

    @Test
    void testFindById_Failure() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            ResponseEntity<Book> responseEntity = bookController.getBook(1L);
        } catch (BookNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No Book found with ID: 1");
        }
    }
}

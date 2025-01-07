package com.example.book_controller.book.data;

import com.example.book_controller.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Book Repository class for managing {@link Book} entities in the database
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

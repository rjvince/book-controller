package com.example.book_controller;

import com.example.book_controller.book.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBookController() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Test GET:/books/{id}
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(status().isOk()).andReturn();

        Book book = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
        assertThat(book.getAuthor()).isEqualTo("Anonymous");
        assertThat(book.getTitle()).isEqualTo("Beowulf");
        assertThat(book.getIsbn()).isEqualTo("0123456789");

        // Test GET:/books
        result = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk()).andReturn();

        List<Book> books = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {
        });
        assertThat(books).hasSize(3);

        // Test GET:/books/{id} for books that aren't there
        mockMvc.perform(MockMvcRequestBuilders.get("/books/6"))
                .andExpect(status().isNotFound());

        // Test PUT:/books/{id}
        Book updateBook = new Book();
        updateBook.setPublishedDate(LocalDateTime.now());
        updateBook.setIsbn("9999999999");
        updateBook.setTitle("fluwoeB");
        updateBook.setAuthor("Unknown");

        result = mockMvc.perform(put("/books/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updateBook))).andReturn();
        Book resultUpdateBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
        assertThat(resultUpdateBook.getAuthor()).isEqualTo("Unknown");
        assertThat(resultUpdateBook.getTitle()).isEqualTo("fluwoeB");
        assertThat(resultUpdateBook.getIsbn()).isEqualTo("9999999999");

        // Test DELETE:/books/{id}, positive and negative case
        mockMvc.perform(delete("/books/3")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/books/3")).andExpect(status().isNotFound());

        // Test PUT:/books/{id} on non-existant book
        mockMvc.perform(put("/books/3").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updateBook))).andExpect(status().isNotFound());

        // Test POST:/books
        Book addBook = new Book();
        addBook.setAuthor("Thompson, Hunter S.");
        addBook.setTitle("Fear and Loathing in Las Vegas");
        addBook.setIsbn("7777777777");
        addBook.setPublishedDate(LocalDateTime.now());

        result = mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addBook))).andExpect(status().isCreated()).andReturn();
        Book resultAddBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
        addBook.setId(4L);
        assertThat(resultAddBook).isEqualTo(addBook);
    }
}

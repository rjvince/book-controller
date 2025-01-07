package com.example.book_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.book_controller.book")
public class BookControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookControllerApplication.class, args);
    }
}

package com.demo.onlinebookstore.controller;

import com.demo.onlinebookstore.model.Book;
import com.demo.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/books") // Base path for book-related APIs
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addbook") // Create a new book
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
    	logger.info("Received request to add a new book");
        Book savedBook = bookService.addBook(book);
        logger.info("Book added successfully with ID: {}", savedBook.getId());
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping ("/getallbooks")// Get all books
    public ResponseEntity<List<Book>> getAllBooks() {
    	logger.info("Received request to fetch all books");
        List<Book> books = bookService.getAllBooks();
        logger.info("Returning {} books", books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getbookbyid/{id}") // Get book by ID
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    	 logger.info("Received request to fetch book with ID: {}", id);
         Book book = bookService.getBookById(id);
         logger.info("Returning book with ID : {}", id);
         return ResponseEntity.ok(book);
    }

    @PutMapping("/updatebookbyid/{id}") // Update book by ID
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    	logger.info("Received request to update book with ID: {}", id);
    	System.out.println("update book by id:");
        Book book = bookService.updateBook(id, updatedBook);
        logger.info("Book with ID {} updated successfully", id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/deletebookbyid/{id}") // Delete book by ID
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
    	logger.info("Received request to delete book with ID: {}", id);
        bookService.deleteBook(id);
        logger.info("Book with ID {} deleted successfully", id);
        return ResponseEntity.ok("Book with ID " + id + " deleted successfully.");
    }
}

package com.demo.onlinebookstore.service;

import com.demo.onlinebookstore.model.Book;
import com.demo.onlinebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.onlinebookstore.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service // Marks this class as a service component
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    // Adds a new book to the database
    public Book addBook(Book book) {
    	logger.info("Adding a new book");
        Book savedBook = bookRepository.save(book);
        logger.info("Book added successfully with ID: {}", savedBook.getId());
        return savedBook;
    }

    // Retrieves all books from the database
    public List<Book> getAllBooks() {
    	logger.info("Fetching all books from the database");
        List<Book> books = bookRepository.findAll();
        logger.info("Retrieved {} books", books.size());
        return books;
    }

    // Retrieves a book by its ID, throws an exception if not found
    public Book getBookById(Long id) {
    	logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book with ID {} not found", id);
                    return new BookNotFoundException("Book with ID " + id + " not found.");
                });
    }

    // Updates an existing book, throws an exception if the book does not exist
    public Book updateBook(Long id, Book updatedBook) {
    	logger.info("Updating book with ID: {}", id);
        return bookRepository.findById(id)
                .map(book -> {
                    logger.info("Updating details for book ID: {}", id);
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    book.setPublishedDate(updatedBook.getPublishedDate());
                    Book savedBook = bookRepository.save(book);
                    logger.info("Book with ID {} updated successfully", id);
                    return savedBook;
                })
                .orElseThrow(() -> {
                    logger.error("Book with ID {} not found for update", id);
                    return new BookNotFoundException("Book with ID " + id + " not found.");
                });
    }

    // Deletes a book by its ID, throws an exception if not found
    public void deleteBook(Long id) {
    	 logger.info("Deleting book with ID: {}", id);
         if (!bookRepository.existsById(id)) {
             logger.error("Book with ID {} not found for deletion", id);
             throw new BookNotFoundException("Book with ID " + id + " not found.");
         }
         bookRepository.deleteById(id);
         logger.info("Book with ID {} deleted successfully", id);
    }
}

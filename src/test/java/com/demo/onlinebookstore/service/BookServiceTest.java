package com.demo.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.demo.onlinebookstore.model.Book;
import com.demo.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Book", "Author Name", new BigDecimal("9.99"), LocalDate.of(2024, 1, 1));
    }

    // Test Get Book By ID
    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    // Test Get All Books
    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Spring Boot", "Sivanesh", new BigDecimal("10.00"), LocalDate.now()),
                new Book(2L, "Java Basics", "Vijay", new BigDecimal("15.00"), LocalDate.now())
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> retrievedBooks = bookService.getAllBooks();

        assertEquals(2, retrievedBooks.size());
        verify(bookRepository, times(1)).findAll();
    }

    //Test Add Book ()
    @Test
    void testAddBook() {
        Book newBook = new Book(null, "New Book", "New Author", new BigDecimal("12.99"), LocalDate.now());
        Book savedBook = new Book(1L, "New Book", "New Author", new BigDecimal("12.99"), LocalDate.now());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.addBook(newBook);

        assertNotNull(result.getId());
        assertEquals("New Book", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    // Test Update Book
    @Test
    void testUpdateBook() {
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", new BigDecimal("20.00"), LocalDate.now());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }
    
    // Test Delete Book
    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}

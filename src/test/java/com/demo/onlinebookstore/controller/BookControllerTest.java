package com.demo.onlinebookstore.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.onlinebookstore.model.Book;
import com.demo.onlinebookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class) 
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Long BOOK_ID = 1L;
    private static final String TITLE = "Spring Boot";
    private static final String AUTHOR = "Sanjay";
    private static final BigDecimal PRICE = new BigDecimal("450.0");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book(BOOK_ID, TITLE, AUTHOR, PRICE, LocalDate.now());
        when(bookService.getBookById(BOOK_ID)).thenReturn(book);

        mockMvc.perform(get("/books/getbookbyid/{id}", BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.author").value(AUTHOR))
                .andExpect(jsonPath("$.price").value(PRICE));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Spring Boot", "Sivanesh", new BigDecimal("500.0"), LocalDate.now()),
                new Book(2L, "Java Basics", "Vijay", new BigDecimal("300.0"), LocalDate.now())
        );
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books/getallbooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Spring Boot"))
                .andExpect(jsonPath("$[1].title").value("Java Basics"));
    }

    @Test
    public void testAddBook() throws Exception {
        Book newBook = new Book(null, TITLE, AUTHOR, PRICE, LocalDate.now());
        Book savedBook = new Book(BOOK_ID, TITLE, AUTHOR, PRICE, LocalDate.now());
        when(bookService.addBook(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books/addbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(BOOK_ID))
                .andExpect(jsonPath("$.title").value(TITLE));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book updatedBook = new Book(BOOK_ID, "Updated Title", AUTHOR, new BigDecimal("500.0"), LocalDate.now());
        when(bookService.updateBook(eq(BOOK_ID), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/updatebookbyid/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/deletebookbyid/{id}", BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ID " + BOOK_ID + " deleted successfully."));

        verify(bookService, times(1)).deleteBook(BOOK_ID);
    }
}

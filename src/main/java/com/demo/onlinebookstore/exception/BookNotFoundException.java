package com.demo.onlinebookstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.onlinebookstore.exception.BookNotFoundException;

public class BookNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BookNotFoundException.class);

	public BookNotFoundException(String message) {
        super(message);
        logger.error("BookNotFoundException: {}", message);
    }
}



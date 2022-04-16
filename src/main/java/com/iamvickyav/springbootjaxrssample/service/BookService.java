package com.iamvickyav.springbootjaxrssample.service;

import com.iamvickyav.springbootjaxrssample.dto.BookDto;
import com.iamvickyav.springbootjaxrssample.entity.Book;
import com.iamvickyav.springbootjaxrssample.exception.ApiException;
import com.iamvickyav.springbootjaxrssample.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(String author) {
        List<Book> books;
        if(author.isEmpty()) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findByAuthorContaining(author);
        }
        return books;
    }

    public void updateBook(String isbn, BookDto bookDto) {
        Optional<Book> bookByISBN = bookRepository.findById(isbn);
        if(bookByISBN.isPresent()) {
            Book book = bookByISBN.get();
            book.setAuthor(bookDto.getAuthorName());
            book.setName(bookDto.getName());
            bookRepository.save(book);
        } else {
            throw new ApiException("Book not found for given ISBN", Response.Status.NOT_FOUND.getReasonPhrase(),
                    Response.Status.BAD_REQUEST);
        }
    }

    public void deleteBook(String isbn) {
        Optional<Book> bookByISBN = bookRepository.findById(isbn);
        if(bookByISBN.isPresent()) {
            Book book = bookByISBN.get();
            bookRepository.delete(book);
        } else {
            throw new ApiException("Book not found to delete for given ISBN", Response.Status.NOT_FOUND.getReasonPhrase(),
                    Response.Status.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteBooksByAuthor(String author) {
        bookRepository.deleteByAuthor(author);
    }

    public List<Book> createBooksInBulk(List<BookDto> books) {
        List<Book> bookList = books.stream()
                .map(this::convertToBook)
                .collect(Collectors.toList());
        return bookRepository.saveAll(bookList);
    }

    // Random String Generation code from https://www.baeldung.com/java-random-string
    private String generateRandomISBN() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private Book convertToBook(BookDto bookDto) {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthorName());
        book.setIsbn(generateRandomISBN());
        return book;
    }
}

package com.example.bookstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    }
    
    public List<Book> searchBooks(String title, String author, String isbn, String genre) {
        // Mặc định trả về tất cả sách nếu không có tiêu chí tìm kiếm
        if (title == null && author == null && isbn == null && genre == null) {
            return bookRepository.findAll();
        }
        
        // Xây dựng các điều kiện tìm kiếm
        return bookRepository.findAll().stream()
            .filter(book -> 
                (title == null || book.getTitle().toLowerCase().contains(title.toLowerCase())) &&
                (author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase())) &&
                (isbn == null || book.getIsbn().toLowerCase().contains(isbn.toLowerCase())) &&
                (genre == null || (book.getCategory() != null && 
                                 book.getCategory().getName().toLowerCase().contains(genre.toLowerCase())))
            )
            .collect(Collectors.toList());
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public boolean isBookAvailable(Long bookId) {
        Book book = getBookById(bookId);
        return book.getAvailableCopies() > 0;
    }
    
    public void borrowBook(Long bookId) {
        Book book = getBookById(bookId);
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }
    
    public void returnBook(Long bookId) {
        Book book = getBookById(bookId);
        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new RuntimeException("Invalid return operation");
        }
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }
}
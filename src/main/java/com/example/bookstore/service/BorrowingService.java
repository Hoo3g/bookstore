package com.example.bookstore.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bookstore.dto.BorrowingInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Borrowing;
import com.example.bookstore.model.BorrowingStatus;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BorrowingRepository;

@Service
public class BorrowingService {
    
    @Autowired
    private BorrowingRepository borrowingRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;

    private static final int DEFAULT_BORROW_DAYS = 14;

    @Transactional
    public Borrowing borrowBook(Long userId, Long bookId) {
        User user = userService.findById(userId);
        Book book = bookService.getBookById(bookId);

        if (!bookService.isBookAvailable(bookId)) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(DEFAULT_BORROW_DAYS));
        borrowing.setStatus(BorrowingStatus.BORROWED);
        borrowing.setFineAmount(BigDecimal.ZERO);

        bookService.borrowBook(bookId);
        return borrowingRepository.save(borrowing);
    }

    @Transactional
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        if (borrowing.getStatus() == BorrowingStatus.RETURNED) {
            throw new RuntimeException("Book is already returned");
        }

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.RETURNED);
        bookService.returnBook(borrowing.getBook().getId());


        if (borrowing.getDueDate().isBefore(LocalDate.now())) {
            long daysOverdue = borrowing.getDueDate().until(LocalDate.now()).getDays();
            BigDecimal finePerDay = new BigDecimal("1.00");
            borrowing.setFineAmount(finePerDay.multiply(new BigDecimal(daysOverdue)));
        }

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getUserBorrowings(Long userId) {
        User user = userService.findById(userId);
        return borrowingRepository.findByUser(user);
    }

    public List<BorrowingInfoDTO> getAllBorrowingInfo() {
        List<Object[]> results = borrowingRepository.findAllBorrowingInfoRaw();

        return results.stream().map(r -> new BorrowingInfoDTO(
                (Long) r[0],
                (String) r[1],
                (String) r[2],
                (LocalDate) r[3],
                r[4] != null ? (LocalDate) r[4] : null,
                r[5].toString()
        )).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * *") // Run at midnight every day
    public void checkOverdueBorrowings() {
        List<Borrowing> activeBorrowings = borrowingRepository.findByStatus(BorrowingStatus.BORROWED);
        LocalDate today = LocalDate.now();

        for (Borrowing borrowing : activeBorrowings) {
            if (borrowing.getDueDate().isBefore(today)) {
                borrowing.setStatus(BorrowingStatus.OVERDUE);

                long daysOverdue = borrowing.getDueDate().until(today).getDays();
                BigDecimal finePerDay = new BigDecimal("1.00"); // $1 per day
                borrowing.setFineAmount(finePerDay.multiply(new BigDecimal(daysOverdue)));
                
                borrowingRepository.save(borrowing);
            }
        }
    }

    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }
}
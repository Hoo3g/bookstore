package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class BorrowingInfoDTO {
    private Long userId;
    private String userName;
    private String bookTitle;
    private LocalDate  borrowDate;
    private LocalDate  returnDate;
    private String status;

    public BorrowingInfoDTO(Long userId, String userName, String bookTitle,
                            LocalDate  borrowDate, LocalDate  returnDate, String status) {
        this.userId = userId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}
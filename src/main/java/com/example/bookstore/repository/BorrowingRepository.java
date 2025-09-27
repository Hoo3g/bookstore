package com.example.bookstore.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bookstore.dto.BorrowingInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.model.Borrowing;
import com.example.bookstore.model.BorrowingStatus;
import com.example.bookstore.model.User;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByUser(User user);
    List<Borrowing> findByStatus(BorrowingStatus status);
    List<Borrowing> findByUserAndStatus(User user, BorrowingStatus status);
    Page<Borrowing> findByUser(User user, Pageable pageable);
    List<Borrowing> findByDueDateBeforeAndStatus(LocalDateTime date, BorrowingStatus status);

    @Query("SELECT u.id, u.fullName, bk.title, b.borrowDate, b.returnDate, b.status " +
            "FROM Borrowing b " +
            "JOIN b.user u " +
            "JOIN b.book bk " +
            "ORDER BY b.borrowDate DESC")
    List<Object[]> findAllBorrowingInfoRaw();
}
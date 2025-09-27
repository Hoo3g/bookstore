package com.example.bookstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.bookstore.dto.BorrowRequest;
import com.example.bookstore.dto.BorrowingInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bookstore.model.Borrowing;
import com.example.bookstore.service.BorrowingService;

@RestController
@RequestMapping("/api/borrowings")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> borrowBook(@RequestBody BorrowRequest request) {
        try {
            Borrowing borrowing = borrowingService.borrowBook(request.getUserId(), request.getBookId());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", borrowing);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllBorrowingInfo() {
        List<BorrowingInfoDTO> borrowings = borrowingService.getAllBorrowingInfo();

        List<Map<String, Object>> data = borrowings.stream().map(b -> {
            Map<String, Object> m = new HashMap<>();
            m.put("userId", b.getUserId());
            m.put("userName", b.getUserName());
            m.put("bookTitle", b.getBookTitle());
            m.put("borrowDate", b.getBorrowDate());
            m.put("returnDate", b.getReturnDate());
            m.put("status", b.getStatus());
            m.put("actions", "");
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Map<String, Object>> returnBook(@PathVariable Long id) {
        try {
            Borrowing borrowing = borrowingService.returnBook(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", borrowing);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserBorrowings(@PathVariable Long userId) {
        try {
            List<Borrowing> borrowings = borrowingService.getUserBorrowings(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", borrowings);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBorrowingById(@PathVariable Long id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", borrowing);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password; // giữ tên đơn giản
    private String fullName;
    // getter/setter
}

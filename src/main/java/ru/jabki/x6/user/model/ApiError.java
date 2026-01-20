package ru.jabki.x6.user.model;

import lombok.Data;

@Data
public class ApiError {
    final boolean success;
    final String message;
}
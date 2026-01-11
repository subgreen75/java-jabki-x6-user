package ru.jabki.x6.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private long id;
    private String login;
    private String first_name;
    private String last_name;
    private String email;
}
package ru.jabki.x6.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.jabki.x6.user.model.ApiStatusFind;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }


    @GetMapping("/exists/{id}")
    @Operation(summary = "Проверить существует ли пользователь по id")
    public ResponseEntity<ApiStatusFind> existsById(@PathVariable("id") Long id) {
        Boolean exists = userService.existsById(id);
        return ResponseEntity.ok()
                .body(
                        new ApiStatusFind(
                                exists,
                                (exists ? "Пользователь с id " + id + " найден" : "Пользователя с id " + id + " не существует")
                        )
                );
    }
}
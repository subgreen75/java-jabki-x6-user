package ru.jabki.x6.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ru.jabki.x6.user.exception.UserException;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.repository.UserRepository;
import ru.jabki.x6.user.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid() {
        final User user = getUser();
        when(userRepository.insert(user)).thenReturn(user);
        User result = userService.create(user);
        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void createUser_fail_bademail() {
        final User user = getUser();
        user.setEmail("bademail");
        final UserException exception = assertThrows(
                UserException.class,
                () -> userService.create(user)
        );
        assertEquals(exception.getMessage(), "Некорректное значение email");
        verify(userRepository, never()).insert(any());
    }

    @Test
    void getUser_valid() {
        final User user = getUser();
        when(userRepository.getById(user.getId())).thenReturn(user);
        User result = userService.getById(user.getId());
        assertThat(result).isEqualTo(user);
        verify(userRepository).getById(user.getId());
    }

    @Test
    void deleteUser_valid() {
        long id = 1L;
        doNothing().when(userRepository).delete(id);
        userService.delete(id);
        verify(userRepository).delete(id);
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .first_name("Some name")
                .last_name("Some name")
                .email("mail@mail.ru")
                .login("some login")
                .build();
    }
}
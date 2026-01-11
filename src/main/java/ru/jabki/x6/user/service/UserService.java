package ru.jabki.x6.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import ru.jabki.x6.user.exception.UserException;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User create(User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(long id) {
        final User user = userRepository.getById(id);
        if (user == null) {
            throw new UserException("Пользователь по id " + id + " не найден");
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(User user) {
        validate(user);
        User existsUser = getById(user.getId());
        existsUser.setFirst_name(user.getFirst_name());
        existsUser.setLast_name(user.getLast_name());
        existsUser.setEmail(user.getEmail());
        existsUser.setLogin(user.getLogin());
        return userRepository.update(user);
    }

    private void validate(User user) {
        if (user == null) {
            throw new UserException("Пользователь не может быть пустым");
        }

        if (!StringUtils.hasText(user.getFirst_name())) {
            throw new UserException("Имя пользователя не может быть пустым");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("Емейл пользователя не может быть пустым");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Логин пользователя не может быть пустым");
        }

        if (!Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(user.getEmail())
                .matches()) {
            throw new UserException("Некорректное значение email");
        }
    }

    @Transactional(readOnly = true)
    public Boolean existsById(long id) {
        try {
            final User user = userRepository.getById(id);
            return user != null;
        } catch (Exception e) {
            return false;
        }
    }
}
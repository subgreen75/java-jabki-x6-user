package ru.jabki.x6.user.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.x6.user.exception.BadRequestException;
import ru.jabki.x6.user.model.User;

@Repository
@AllArgsConstructor
public class UserRepository {
    private static final String INSERT = """
               INSERT INTO x6_user.user(login, first_name, last_name, email)
               VALUES (:login, :first_name, :last_name, :email)
               RETURNING *;   
            """;

    private static final String UPDATE = """
            UPDATE x6_user.user
            SET login = :login, first_name = :first_name, last_name = :last_name, email = :email
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM x6_user.user
            WHERE id = :id;
            """;
    private static final String GET_BY_ID = """
            SELECT * 
            FROM x6_user.user
            WHERE id = :id;
            """;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public void delete(final Long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        } catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id: %d не найден", id));
        }
    }

    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id: %d не найден", id));
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("login", user.getLogin());
        params.addValue("first_name", user.getFirst_name());
        params.addValue("last_name", user.getLast_name());
        params.addValue("email", user.getEmail());
        return params;
    }
}
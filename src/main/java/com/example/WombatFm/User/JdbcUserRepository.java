package com.example.WombatFm.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) throws Exception {
        String sql ="INSERT INTO users (username, password, role, is_active) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), "member", true);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, username);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<User> getAllUsersPaginated(int limit, int offset) {
        String sql = """
                SELECT * FROM users
                ORDER BY user_id DESC LIMIT ? OFFSET ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToUser, limit, offset);
    }

    @Override
    public void updateUserRole(int userId, String role) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, role, userId);
    }

    @Override
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getInt("user_id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("password"),
            resultSet.getString("role"),
            resultSet.getBoolean("is_active")
        );
    }
}

package com.example.WombatFm.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user) throws Exception;
    Optional<User> findByUsername(String username);
    List<User> getAllUsersPaginated(int limit, int offset);
    int countUsers();
    void updateUserRow(int userId, String role, boolean isActive);
}

package com.example.WombatFm.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user) throws Exception;
    Optional<User> findByUsername(String username);
}

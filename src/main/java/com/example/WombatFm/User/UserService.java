package com.example.WombatFm.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public User login(String username, String password) {
        Optional<User> presentUser = this.userRepository.findByUsername(username);
        if(!presentUser.isPresent()) {
            return null;
        }

        User user = presentUser.get();
        if(!passwordEncoder.matches(password, presentUser.get().getPassword())) {
            return null;
        }

        return user;
    }
}
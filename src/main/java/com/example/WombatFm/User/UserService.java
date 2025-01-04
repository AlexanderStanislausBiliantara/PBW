package com.example.WombatFm.User;

import java.util.List;

import java.util.Map;
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

    public List<User> getUsersWithPagination(int page, int size) {
        int offset = (page - 1) * size;
        return userRepository.getAllUsersPaginated(size, offset);
    }

    public int getTotalUserCount() {
        return userRepository.countUsers();
    }

    public int getUserPageCount(int size) {
        int rowCount = getTotalUserCount();
        return (int) Math.ceil((double) rowCount / size);
    }


    public void updateUserRow(int userId, String role, boolean isActive) {
        this.userRepository.updateUserRow(userId, role, isActive);
    }

    // public void updateMultipleUserRole(Map<Integer, String> userRoles) {
    //     for(Map.Entry<Integer, String> entry : userRoles.entrySet()) {
    //         this.userRepository.updateUserRole(entry.getKey(), entry.getValue());
    //     }
    // }

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

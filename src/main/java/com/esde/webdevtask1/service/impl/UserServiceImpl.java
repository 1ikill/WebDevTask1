package com.esde.webdevtask1.service.impl;

import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.repository.UserRepository;
import com.esde.webdevtask1.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void register(String username, String email, String password) {
        User user = new User(username, email, password);
        if (userExists(email)){
            throw new IllegalArgumentException("User already exists");
        }
        userRepository.insertUser(user);
    }

    @Override
    public boolean userExists(String email) {
        User user = userRepository.selectUserByEmail(email);
        return user !=null;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.selectUserByEmail(email);
        return user;
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.selectUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public Boolean updateUsername(String username, int userId) {
        boolean usernameUpdated = userRepository.updateUser(username, userId);
        return usernameUpdated;
    }

    @Override
    public boolean deleteUser(String email) {
        boolean userDeleted = userRepository.deleteUser(email);
        return userDeleted;

    }
}

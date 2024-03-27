package com.esde.webdevtask1.service;

import com.esde.webdevtask1.model.User;

public interface UserService {
     void register(String username, String email, String password);

     User login(String email, String password);

     boolean userExists(String email);

     User getUserByEmail(String email);

     Boolean updateUsername(String username, int userId);

     boolean deleteUser(String email);
}

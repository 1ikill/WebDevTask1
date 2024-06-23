package com.esde.webdevtask1.service;

import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.service.exception.ServiceException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

public interface UserService {
    void register(String username, String email, String password) throws ServiceException;

    User login(String email, String password) throws ServiceException;

    boolean userExists(String email) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    boolean updateUsername(String username, int userId) throws ServiceException;

    boolean deleteUser(String email) throws ServiceException;

    boolean uploadFile(InputStream file, int userId) throws ServiceException;

    String findUserImage(String email, ServletContext context) throws IOException, ServiceException;

    void verifyUser(String email) throws ServiceException;

    boolean userVerified(String email) throws ServiceException;
}

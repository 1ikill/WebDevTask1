package com.esde.webdevtask1.service.impl;

import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.repository.UserRepository;
import com.esde.webdevtask1.repository.exception.RepositoryException;
import com.esde.webdevtask1.service.UserService;
import com.esde.webdevtask1.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import org.apache.commons.io.IOUtils;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void register(String username, String email, String password) throws ServiceException {
        User user = new User(username, email, password);
        if (userExists(email)){
            throw new ServiceException("User already exists");
        }

        try {
            userRepository.insertUser(user);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean userExists(String email) throws ServiceException {
        User user;
        try{
            user = userRepository.selectUserByEmail(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return user !=null;
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        User user;
        try {
            user = userRepository.selectUserByEmail(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User login(String email, String password) throws ServiceException {
        User user;
        try {
            user = userRepository.selectUserByEmail(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean updateUsername(String username, int userId) throws ServiceException {
        boolean usernameUpdated;
        try {
            usernameUpdated = userRepository.updateUser(username, userId);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return usernameUpdated;
    }

    @Override
    public boolean deleteUser(String email) throws ServiceException {
        boolean userDeleted;
        try{
            userDeleted = userRepository.deleteUser(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return userDeleted;

    }

    @Override
    public boolean uploadFile(InputStream file, int userId) throws ServiceException {
        boolean fileUploaded;
        try {
            fileUploaded = userRepository.uploadFile(file, userId);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return fileUploaded;
    }

    @Override
    public String findUserImage(String email, ServletContext context) throws IOException, ServiceException {
        Optional<InputStream> optionalImageStream;
        try {
            optionalImageStream = userRepository.findUsersImage(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        String base64Image;
        if (optionalImageStream.isPresent()){
            InputStream imageStream = optionalImageStream.get();
            byte[] imageBytes = IOUtils.toByteArray(imageStream);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return base64Image;
        }
        String fullPath = context.getRealPath("/images/default.png");
        Path path = Paths.get(fullPath);
        byte[] imageBytes = Files.readAllBytes(path);
        base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }

    @Override
    public void verifyUser(String email) throws ServiceException {
            try {
                userRepository.verifyUser(email);
            } catch (RepositoryException e){
                logger.error(e);
                throw new ServiceException(e);
            }
    }

    @Override
    public boolean userVerified(String email) throws ServiceException {
        boolean userVerified;
        try {
            userVerified = userRepository.userVerified(email);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return userVerified;
    }
}

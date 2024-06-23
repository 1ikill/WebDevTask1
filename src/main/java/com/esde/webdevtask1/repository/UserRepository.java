package com.esde.webdevtask1.repository;

import com.esde.webdevtask1.model.User;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import com.esde.webdevtask1.pool.DatabasePool;
import com.esde.webdevtask1.repository.exception.RepositoryException;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepository {
    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_USER = "insert into users (username, email, password) values (?, ?, ?);";
    private static final String DELETE_USER = "delete from users where email = ?;";
    private static final String UPDATE_USERNAME = "update users set username = ? where id = ?;";
    private static final String SELECT_USER_BY_EMAIL = "select id, username, email, password from users where email =?";
    private static final String SELECT_USER_IMAGE = "select user_file from users where email =?";
    private static final String UPLOAD_FILE = "update users set user_file = ? where id = ?";
    private static final String SELECT_VERIFIED = "SELECT verified FROM users WHERE email = ?";
    private static final String VERIFY_USER = "UPDATE users SET verified = true WHERE email = ?";

    public UserRepository(){}

    public void insertUser(User user) throws RepositoryException {
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.executeUpdate();
            logger.info("INSERT USER successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error inserting user");
        }
    }

    public User selectUserByEmail(String email) throws RepositoryException {
        User user = null;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(id, username, email, password);
            }
            logger.info("SELECT user BY EMAIL successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error selecting user by email");
        }
        return user;
    }

    public boolean updateUser(String username, int id) throws RepositoryException {
        boolean rowUpdated ;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERNAME)) {
            statement.setString(1,username);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE user successful= " + rowUpdated);
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error updating user");
        }
        return rowUpdated;
    }

    public boolean deleteUser(String email) throws RepositoryException{
        boolean rowDeleted;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, email);
            rowDeleted = statement.executeUpdate() > 0;
            logger.info("DELETE user successful= " + rowDeleted);
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error deleting user");
        }
        return rowDeleted;
    }

    public boolean uploadFile(InputStream file, int id) throws RepositoryException {
        boolean rowUpdated;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPLOAD_FILE)) {
            statement.setBinaryStream(1, file);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("Upload image successful= " + rowUpdated);
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error uploading image");
        }
        return rowUpdated;
    }

    public Optional<InputStream> findUsersImage(String email) throws RepositoryException {
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_IMAGE)) {
            statement.setString(1,email);
           ResultSet rs = statement.executeQuery();
            rs.next();
            InputStream inputStream = rs.getBinaryStream(1);
            if (inputStream!= null){
                return Optional.of(inputStream);
            }
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error finding user image");
        }
        return Optional.empty();
    }

    public void verifyUser(String email) throws RepositoryException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(VERIFY_USER)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RepositoryException("Error verifying user");
        }
    }

    public boolean userVerified(String email) throws RepositoryException {
        boolean userVerified = false;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_VERIFIED)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userVerified = rs.getBoolean("verified");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RepositoryException("Error checking user verification");
        }
        return userVerified;
    }
}

package com.esde.webdevtask1.repository;

import com.esde.webdevtask1.model.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepository {
    private static final Logger logger = LogManager.getLogger();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/webdev";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "postgres";
    private static final String INSERT_USER = "insert into users" + "  (username, email, password) values "
            + " (?, ?, ?);";
    private static final String DELETE_USER = "delete from users where email = ?;";
    private static final String UPDATE_USERNAME = "update users set username = ? where id = ?;";
    private static final String SELECT_USER_BY_EMAIL = "select id, username, email, password from users where email =?";

    public UserRepository(){}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public void insertUser(User user){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.executeUpdate();
            logger.info("INSERT USER successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public User selectUserByEmail(String email) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);) {
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
        }
        return user;
    }

    public boolean updateUser(String username, int id) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERNAME)) {
            statement.setString(1,username);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE user successful= " + rowUpdated);
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return rowUpdated;
    }

    public boolean deleteUser(String email) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER);) {
            statement.setString(1, email);
            rowDeleted = statement.executeUpdate() > 0;
            logger.info("DELETE user successful= " + rowDeleted);
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return rowDeleted;
    }
}

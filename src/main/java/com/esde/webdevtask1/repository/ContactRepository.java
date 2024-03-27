package com.esde.webdevtask1.repository;

import com.esde.webdevtask1.model.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactRepository {
    private static final Logger logger = LogManager.getLogger();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/webdev";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "postgres";
    private static final String INSERT_CONTACT = "insert into contacts" + "  (name, number, userId) values "
            + " (?, ?, ?);";
    private static final String DELETE_CONTACT = "delete from contacts where id = ?;";
    private static final String UPDATE_CONTACT_NAME = "update contacts set name = ? where id = ?;";
    private static final String UPDATE_CONTACT_NUMBER = "update contacts set number = ? where id = ?;";
    private static final String SELECT_CONTACT_BY_NAME = "select id, name, number, userId from contacts where name =? and userId =?;";
    private static final String SELECT_ALL_CONTACTS = "select name, number from contacts where userId =?";

    public ContactRepository() {
    }

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

    public void insertContact(Contact contact) {
        System.out.println(INSERT_CONTACT);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACT)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getNumber());
            preparedStatement.setInt(3, contact.getUserId());
            preparedStatement.executeUpdate();
            logger.info("INSERT contact successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public List<Contact> selectAllContacts(int id) {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String number = rs.getString("number");
                contacts.add(new Contact(name, number));
            }
            logger.info("SELECT ALL contacts successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return contacts;
    }

    public Contact selectContactByName(String name, int userId) {
        Contact contact = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTACT_BY_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                contact = new Contact(id, name, number, userId);
            }
            logger.info("SELECT contact BY NAME successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return contact;
    }

    public boolean updateContactName(String name, int id) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_NAME)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE contact NAME successful= " + rowUpdated);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return rowUpdated;
    }

    public boolean updateContactNumber(String number, int id) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_NUMBER)) {
            statement.setString(1, number);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE contact NUMBER successful= " + rowUpdated);
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return rowUpdated;
    }

    public boolean deleteContact(int id) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
            logger.info("DELETE contact successful= " + rowDeleted);
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return rowDeleted;
    }
}

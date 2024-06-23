package com.esde.webdevtask1.repository;

import com.esde.webdevtask1.model.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.esde.webdevtask1.pool.DatabasePool;
import com.esde.webdevtask1.repository.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactRepository {
    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_CONTACT = "insert into contacts" + "  (name, number, userId) values "
            + " (?, ?, ?);";
    private static final String DELETE_CONTACT = "delete from contacts where id = ?;";
    private static final String UPDATE_CONTACT_NAME = "update contacts set name = ? where id = ?;";
    private static final String UPDATE_CONTACT_NUMBER = "update contacts set number = ? where id = ?;";
    private static final String SELECT_CONTACT_BY_NAME = "select id, name, number, userId from contacts where name =? and userId =?;";
    private static final String SELECT_ALL_CONTACTS = "select id, name, number from contacts where userId =? order by id LIMIT ? offset ?";
    private static final String SELECT_NUMBER_OF_CONTACTS =  "SELECT COUNT(*) FROM contacts WHERE userid = ?";

    public ContactRepository() {
    }

    public void insertContact(Contact contact) throws RepositoryException {
        System.out.println(INSERT_CONTACT);
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACT)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getNumber());
            preparedStatement.setInt(3, contact.getUserId());
            preparedStatement.executeUpdate();
            logger.info("INSERT contact successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error inserting contacts");
        }
    }

    public List<Contact> selectAllContacts(int id, int pageNum, int pageSize) throws RepositoryException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3,(pageNum -1) * pageSize);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String number = rs.getString("number");
                contacts.add(new Contact(name, number));
            }
            logger.info("SELECT ALL contacts successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error selecting all contacts");
        }
        return contacts;
    }

    public Contact selectContactByName(String name, int userId) throws RepositoryException {
        Contact contact = null;
        try (Connection connection = DatabasePool.getConnection();
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
            throw new RepositoryException("Error selecting contact by name");
        }
        return contact;
    }

    public boolean updateContactName(String name, int id) throws RepositoryException {
        boolean rowUpdated ;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_NAME)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE contact NAME successful= " + rowUpdated);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error updating contact name");
        }
        return rowUpdated;
    }

    public boolean updateContactNumber(String number, int id) throws RepositoryException {
        boolean rowUpdated;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_NUMBER)) {
            statement.setString(1, number);
            statement.setInt(2, id);
            rowUpdated = statement.executeUpdate() > 0;
            logger.info("UPDATE contact NUMBER successful= " + rowUpdated);
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error updating contact number");
        }
        return rowUpdated;
    }

    public boolean deleteContact(int id) throws RepositoryException {
        boolean rowDeleted ;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
            logger.info("DELETE contact successful= " + rowDeleted);
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new RepositoryException("Error deleting contact");
        }
        return rowDeleted;
    }

    public int findNumberOfContacts(int id) throws RepositoryException {
        int numOfContacts = 0;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_NUMBER_OF_CONTACTS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numOfContacts = rs.getInt("count");
            }
            logger.info("SELECT number of contacts successful");
        } catch (SQLException e) {
            logger.error(e);
            throw new RepositoryException("Error finding the numbers of contacts");
        }
        return numOfContacts;
    }
}

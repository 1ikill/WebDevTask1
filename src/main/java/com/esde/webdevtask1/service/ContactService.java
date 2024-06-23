package com.esde.webdevtask1.service;

import com.esde.webdevtask1.model.Contact;
import com.esde.webdevtask1.service.exception.ServiceException;
import java.util.List;

public interface ContactService {
    List<Contact> getContacts(int userId, int pageNum, int pageSize) throws ServiceException;

    int findNumberOfContacts(int userId) throws ServiceException;

    void createContact(String name, String number, int userId) throws ServiceException;

    boolean updateContactName(String name, String newName, int userId) throws ServiceException;

    boolean updateContactNumber(String name,String newNumber, int userId) throws ServiceException;

    boolean deleteContact(String name, int userId) throws ServiceException;
}

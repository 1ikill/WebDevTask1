package com.esde.webdevtask1.service.impl;

import com.esde.webdevtask1.model.Contact;
import com.esde.webdevtask1.repository.ContactRepository;
import com.esde.webdevtask1.repository.exception.RepositoryException;
import com.esde.webdevtask1.service.ContactService;
import com.esde.webdevtask1.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private final static Logger logger = LogManager.getLogger();

    private final ContactRepository contactRepository = new ContactRepository();

    @Override
    public List<Contact> getContacts(int userId, int pageNum, int pageSize) throws ServiceException {
        List<Contact> contacts;
        try {
            contacts = contactRepository.selectAllContacts(userId, pageNum, pageSize);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return contacts;
    }

    @Override
    public int findNumberOfContacts(int userId) throws ServiceException {
        int numberOfContacts;
        try {
            numberOfContacts = contactRepository.findNumberOfContacts(userId);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return numberOfContacts;
    }

    @Override
    public void createContact(String name, String number, int userId) throws ServiceException {
        Contact contact = new Contact(name, number, userId);
        try {
            contactRepository.insertContact(contact);
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateContactName(String name, String newName, int userId) throws ServiceException {
        Contact contact;
        boolean nameUpdated;
        try {
            contact = contactRepository.selectContactByName(name, userId);
            if (contact == null){
                throw new ServiceException("No such contact");
            }
            nameUpdated = contactRepository.updateContactName(newName, contact.getContactId());
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return nameUpdated;
    }

    @Override
    public boolean updateContactNumber(String name, String newNumber, int userId) throws ServiceException {
        Contact contact;
        boolean numberUpdated;
        try {
            contact = contactRepository.selectContactByName(name, userId);
            if (contact == null){
                throw new ServiceException("No such contact");
            }
            numberUpdated = contactRepository.updateContactNumber(newNumber, contact.getContactId());
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return numberUpdated;
    }

    @Override
    public boolean deleteContact(String name, int userId) throws ServiceException {
        Contact contact;
        boolean contactDeleted;
        try {
            contact = contactRepository.selectContactByName(name, userId);
            if (contact == null){
                throw new ServiceException("No such contact");
            }
            contactDeleted = contactRepository.deleteContact(contact.getContactId());
        } catch (RepositoryException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return contactDeleted;
    }
}

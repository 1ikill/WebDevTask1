package com.esde.webdevtask1.service.impl;

import com.esde.webdevtask1.model.Contact;
import com.esde.webdevtask1.repository.ContactRepository;
import com.esde.webdevtask1.service.ContactService;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository = new ContactRepository();

    @Override
    public List<Contact> getContacts(int userId) {
        List<Contact> contacts;
        contacts = contactRepository.selectAllContacts(userId);
        return contacts;
    }

    @Override
    public void createContact(String name, String number, int userId) {
        Contact contact = new Contact(name, number, userId);
        contactRepository.insertContact(contact);
    }

    @Override
    public Boolean updateContactName(String name, String newName, int userId) {
        Contact contact = contactRepository.selectContactByName(name, userId);
        boolean nameUpdated = contactRepository.updateContactName(newName, contact.getContactId());
        return nameUpdated;
    }

    @Override
    public Boolean updateContactNumber(String name, String newNumber, int userId) {
        Contact contact = contactRepository.selectContactByName(name, userId);
        boolean numberUpdated = contactRepository.updateContactNumber(newNumber, contact.getContactId());
        return numberUpdated;
    }

    @Override
    public boolean deleteContact(String name, int userId) {
        Contact contact = contactRepository.selectContactByName(name, userId);
        boolean userDeleted = contactRepository.deleteContact(contact.getContactId());
        return userDeleted;
    }
}

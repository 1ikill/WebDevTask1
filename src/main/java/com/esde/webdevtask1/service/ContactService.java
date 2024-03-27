package com.esde.webdevtask1.service;
import com.esde.webdevtask1.model.Contact;
import java.util.List;

public interface ContactService {
    List<Contact> getContacts(int userId);

    void createContact(String name, String number, int userId);

    Boolean updateContactName(String name, String newName, int userId);

    Boolean updateContactNumber(String name,String newNumber, int userId);

    boolean deleteContact(String name, int userId);
}

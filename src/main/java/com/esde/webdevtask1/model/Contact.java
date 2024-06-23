package com.esde.webdevtask1.model;

public class Contact {
    private int contactId;
    private String name;
    private String number;
    private int userId;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact(String name, String number, int userId) {
        this.name = name;
        this.number = number;
        this.userId = userId;
    }

    public Contact(int contactId, String name, String number, int userId) {
        this.contactId = contactId;
        this.name = name;
        this.number = number;
        this.userId = userId;
    }

    public Contact() {}

    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Contact{" +
               "contactId=" + contactId +
               ", name='" + name + '\'' +
               ", number='" + number + '\'' +
               ", userId=" + userId +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (contactId != contact.contactId) return false;
        if (userId != contact.userId) return false;
        if (!name.equals(contact.name)) return false;
        return number.equals(contact.number);
    }

    @Override
    public int hashCode() {
        int result = contactId;
        result = 31 * result + name.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + userId;
        return result;
    }
}

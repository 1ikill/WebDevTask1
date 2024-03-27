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

    public Contact(String name, String number, int userId){
        this.name = name;
        this.number = number;
        this.userId = userId;
    }

    public Contact(int contactId, String name, String number, int userId){
        this.contactId = contactId;
        this.name = name;
        this.number = number;
        this.userId = userId;
    }

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

}

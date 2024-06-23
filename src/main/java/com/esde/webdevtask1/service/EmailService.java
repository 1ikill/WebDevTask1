package com.esde.webdevtask1.service;

import com.esde.webdevtask1.service.exception.ServiceException;

public interface EmailService {
    String createCode();

    void sendEmail(String email, String code) throws ServiceException;
}

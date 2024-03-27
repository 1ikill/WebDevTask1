package com.esde.webdevtask1.service;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {
    void createSession(HttpServletRequest request, String email);
}
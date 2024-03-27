package com.esde.webdevtask1.service.impl;

import com.esde.webdevtask1.service.SessionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionServiceImpl implements SessionService{
    @Override
    public void createSession(HttpServletRequest request, String email) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
    }
}

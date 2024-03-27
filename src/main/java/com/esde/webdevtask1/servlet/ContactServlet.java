package com.esde.webdevtask1.servlet;

import javax.servlet.annotation.WebServlet;

import com.esde.webdevtask1.model.Contact;
import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.service.ContactService;
import com.esde.webdevtask1.service.UserService;
import com.esde.webdevtask1.service.impl.ContactServiceImpl;
import com.esde.webdevtask1.service.impl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/contacts", "/new-contact", "/update-contact-name", "/update-contact-number", "/delete-contact"})
public class ContactServlet extends HttpServlet{
    private static final Logger logger = LogManager.getLogger();
    private String action;
    private final UserService userService = new UserServiceImpl();
    private final ContactService contactService = new ContactServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("POST");
        action = request.getServletPath();
        switch (action) {
            case "/new-contact":
                createContact(request, response);
                break;
            case "/update-contact-name":
                updateContactName(request, response);
                break;
            case "/update-contact-number":
                updateContactNumber(request, response);
                break;
            case "/delete-contact":
                deleteContact(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("GET");
        action = request.getServletPath();
        switch (action) {
            case "/contacts":
                getAllContacts(request, response);
                break;
            case "/new-contact":
                showCreateContactForm(request, response);
                break;
            case "/update-contact-name":
                showUpdateContactNameForm(request, response);
                break;
            case "/update-contact-number":
                showUpdateContactNumberForm(request, response);
                break;
            case "/delete-contact":
                showDeleteContactForm(request, response);
                break;
        }
    }
    public void createContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = (String) request.getSession().getAttribute("email");
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        User user = userService.getUserByEmail(userEmail);
        contactService.createContact(name, number, user.getUserId());
        response.sendRedirect("pages/contact-success.jsp");
        logger.info("Contact created");
    }

    public void showCreateContactForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            logger.info("User is not logged in");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
        response.sendRedirect("pages/create-contact.jsp");

    }


    public void getAllContacts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        User user = userService.getUserByEmail(userEmail);
        List<Contact> contacts = contactService.getContacts(user.getUserId());
        request.getSession().setAttribute("contactsList", contacts);
        response.sendRedirect(request.getContextPath() + "/pages/contacts.jsp");
        logger.info("Contacts list returned");
    }

    public void updateContactName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = userService.getUserByEmail(userEmail);
        String name = request.getParameter("name");
        String newName = request.getParameter("newName");
        if (contactService.updateContactName(name, newName, user.getUserId())){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact updated successfully");
        } else {
            logger.info("Update failed");
        }
    }
    public void showUpdateContactNameForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        response.sendRedirect("pages/update-contact-name.jsp");
    }

    public void updateContactNumber(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = userService.getUserByEmail(userEmail);
        String name = request.getParameter("name");
        String newNumber = request.getParameter("newNumber");
        if (contactService.updateContactNumber(name, newNumber, user.getUserId())){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact updated successfully");
        } else {
            logger.info("Update failed");
        }
    }

    public void showUpdateContactNumberForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        response.sendRedirect("pages/update-contact-number.jsp");
    }

    public void deleteContact(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = userService.getUserByEmail(userEmail);
        String name = request.getParameter("name");
        if (contactService.deleteContact(name, user.getUserId())){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact deleted successfully");
        } else {
            logger.info("Delete failed");
        }
    }

    public void showDeleteContactForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        response.sendRedirect("pages/delete-contact.jsp");
    }

    private boolean userLoggedIn(HttpSession session){
        return session != null && session.getAttribute("email") != null;
    }
}

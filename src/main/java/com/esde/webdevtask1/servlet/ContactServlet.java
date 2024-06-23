package com.esde.webdevtask1.servlet;

import javax.servlet.annotation.WebServlet;
import com.esde.webdevtask1.model.Contact;
import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.service.ContactService;
import com.esde.webdevtask1.service.UserService;
import com.esde.webdevtask1.service.exception.ServiceException;
import com.esde.webdevtask1.service.impl.ContactServiceImpl;
import com.esde.webdevtask1.service.impl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/contacts", "/new-contact", "/update-contact-name", "/update-contact-number", "/delete-contact"})
public class ContactServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = new UserServiceImpl();
    private final ContactService contactService = new ContactServiceImpl();
    private String action;

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
        User user = findUserByEmail(request, response, userEmail);

        try {
            contactService.createContact(name, number, user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        response.sendRedirect("pages/contact-success.jsp");
        logger.info("Contact created");
    }

    public void showCreateContactForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/create-contact.jsp").forward(request, response);
    }


    public void getAllContacts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);

        int pageNum = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null){
            pageNum = Integer.parseInt(request.getParameter("page"));
        }

        int numOfRecords = 0;
        List<Contact> contacts = new ArrayList<>();
        try {
            numOfRecords = contactService.findNumberOfContacts(user.getUserId());
            contacts = contactService.getContacts(user.getUserId(), pageNum, pageSize);
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        int numOfPages = (int)Math.ceil(numOfRecords * 1.0
                / pageSize);
        request.getSession().setAttribute("contactsList", contacts);
        request.getSession().setAttribute("currentPage", pageNum);
        request.getSession().setAttribute("numOfPages", numOfPages);
        request.getRequestDispatcher("pages/contacts.jsp").forward(request, response);
        logger.info("Contacts list returned");
    }

    public void updateContactName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);
        String name = request.getParameter("name");
        String newName = request.getParameter("newName");

        boolean contactNameUpdated = false;
        try {
            contactNameUpdated = contactService.updateContactName(name, newName, user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (contactNameUpdated){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact updated successfully");
        } else {
            logger.info("Update failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void showUpdateContactNameForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/update-contact-name.jsp").forward(request, response);
    }

    public void updateContactNumber(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);
        String name = request.getParameter("name");
        String newNumber = request.getParameter("newNumber");

        boolean contactNumberUpdated = false;
        try {
            contactNumberUpdated = contactService.updateContactNumber(name, newNumber, user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (contactNumberUpdated){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact updated successfully");
        } else {
            logger.info("Update failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void showUpdateContactNumberForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/update-contact-number.jsp").forward(request, response);
    }

    public void deleteContact(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);
        String name = request.getParameter("name");

        boolean contactDeleted = false;
        try {
            contactDeleted = contactService.deleteContact(name, user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (contactDeleted){
            response.sendRedirect("pages/contact-success.jsp");
            logger.info("Contact deleted successfully");
        } else {
            logger.info("Delete failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void showDeleteContactForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/delete-contact.jsp").forward(request, response);
    }

    private User findUserByEmail(HttpServletRequest request, HttpServletResponse response, String email) throws ServletException, IOException {
        User user = null;
        try {
            user = userService.getUserByEmail(email);
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }
        return user;
    }
}

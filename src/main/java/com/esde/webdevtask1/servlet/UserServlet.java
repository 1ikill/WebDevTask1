package com.esde.webdevtask1.servlet;

import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.service.EmailService;
import com.esde.webdevtask1.service.SessionService;
import com.esde.webdevtask1.service.UserService;
import com.esde.webdevtask1.service.exception.ServiceException;
import com.esde.webdevtask1.service.impl.EmailServiceImpl;
import com.esde.webdevtask1.service.impl.SessionServiceImpl;
import com.esde.webdevtask1.service.impl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/register", "/login", "/update", "/delete", "/logout", "/user", "/verify", "/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = new UserServiceImpl();
    private final SessionService sessionService = new SessionServiceImpl();
    private final EmailService emailService = new EmailServiceImpl();
    private String action;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("POST");
        action = request.getServletPath();
        switch (action) {
            case "/register":
                register(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            case "/update":
                updateUsername(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            case "/upload":
                uploadFile(request, response);
                break;
            case "/verify":
                verifyUser(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("GET");
        action = request.getServletPath();
        switch (action) {
            case "/register":
                showSignUpForm(request, response);
                break;
            case "/login":
                showLogInForm(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "/user":
                showUser(request, response);
                break;
            case "/update":
                showUpdateForm(request, response);
                break;
            case "/upload":
                showUploadForm(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            userService.register(username, email, password);
            request.getSession().setAttribute("user", new User(username, email, password));
            String code = emailService.createCode();
            emailService.sendEmail(email, code);
            HttpSession session = request.getSession();
            session.setAttribute("code", code);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        logger.info("User created successfully");
        response.sendRedirect("pages/verify.jsp");
    }

    public void showSignUpForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/register.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = null;
        try {
            if (!userService.userVerified(email)) {
                request.getRequestDispatcher("pages/verify.jsp").forward(request, response);
            }

            user = userService.login(email, password);
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        if (user != null) {
            logger.info("User with email " + email + " logged in");
            sessionService.createSession(request, email);
            Cookie usernameCookie = new Cookie("username", user.getUserName());
            Cookie emailCookie = new Cookie("userEmail", user.getEmail());
            usernameCookie.setMaxAge(24 * 60 * 60);
            emailCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(usernameCookie);
            response.addCookie(emailCookie);
            response.sendRedirect("pages/user-menu.jsp");
        } else {
            logger.info("Invalid data for login");
            response.sendRedirect("pages/login.jsp");
        }
    }

    public void showLogInForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/login.jsp").forward(request, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        logger.info("User " + session.getAttribute("email") + " logged out");
        session.invalidate();
        response.sendRedirect("pages/login.jsp");
    }

    public void showUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);

        String image = null;
        try {
            image = userService.findUserImage(userEmail, getServletContext());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userImage", image);
        request.getRequestDispatcher("pages/user.jsp").forward(request, response);
        logger.info("User data returned successfully");

    }

    public void updateUsername(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);
        String username = request.getParameter("username");

        boolean usernameUpdated = false;
        try {
            usernameUpdated = userService.updateUsername(username, user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (usernameUpdated) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        cookie.setValue(username);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
            response.sendRedirect("pages/user-success.jsp");
            logger.info("User updated successfully");
        } else {
            logger.info("Update failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/update-username.jsp").forward(request, response);
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");

        boolean userDeleted = false;
        try {
            userDeleted = userService.deleteUser(userEmail);
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (userDeleted) {
            session.invalidate();
            response.sendRedirect("pages/register.jsp");
        } else {
            logger.info("Delete failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void verifyUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        User user = (User) session.getAttribute("user");

        if (code.equals(session.getAttribute("code"))) {
            try {
                userService.verifyUser(user.getEmail());
            } catch (ServiceException e){
                logger.error(e);
                request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
            }
            response.sendRedirect("pages/registration-successful.jsp");
        } else {
            response.sendRedirect("pages/verify.jsp");
            logger.error("verification failed");
        }
    }

    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("image");
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = findUserByEmail(request, response, userEmail);

        boolean fileUploaded = false;
        try {
            fileUploaded = userService.uploadFile(filePart.getInputStream(), user.getUserId());
        } catch (ServiceException e){
            logger.error(e);
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        if (fileUploaded) {
            response.sendRedirect("pages/user-success.jsp");
            logger.info("User updated successfully");
        } else {
            logger.info("Update failed");
            response.sendRedirect("pages/error/error500.jsp");
        }
    }

    public void showUploadForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("pages/upload.jsp").forward(request, response);
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

package com.esde.webdevtask1.servlet;

import com.esde.webdevtask1.model.User;
import com.esde.webdevtask1.service.SessionService;
import com.esde.webdevtask1.service.UserService;
import com.esde.webdevtask1.service.impl.SessionServiceImpl;
import com.esde.webdevtask1.service.impl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/register", "/login", "/update", "/delete", "/logout", "/user"})
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private String action;
    private final UserService userService = new UserServiceImpl();
    private final SessionService sessionService = new SessionServiceImpl();

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
            case "/delete":
                deleteUser(request, response);
                break;
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        userService.register(username, email, password);
        response.sendRedirect("pages/registration-successful.jsp");
        logger.info("User created successfully");
    }

    public void showSignUpForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("pages/register.jsp");
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userService.login(email, password);
        if (user != null) {
            logger.info("User with email " + email + " logged in");
            sessionService.createSession(request, email);
            response.sendRedirect("pages/user-menu.jsp");
        } else {
            logger.info("Invalid data for login");
            response.sendRedirect("/pages/login.jsp");
        }
    }

    public void showLogInForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("pages/login.jsp");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        logger.info("User " + session.getAttribute("email") + " logged out");
        session.invalidate();
        response.sendRedirect("pages/home.jsp");
    }

    public void showUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        String userEmail = (String) session.getAttribute("email");
        User user = userService.getUserByEmail(userEmail);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/pages/user.jsp");
        logger.info("User data returned successfully");

    }

    public void updateUsername(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = userService.getUserByEmail(userEmail);
        String username = request.getParameter("username");
        if (userService.updateUsername(username, user.getUserId())){
            response.sendRedirect("pages/user-success.jsp");
            logger.info("User updated successfully");
        } else {
            logger.info("Update failed");
        }
    }
    public void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        response.sendRedirect("pages/update-username.jsp");
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (!userLoggedIn(session)){
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            logger.info("User is not logged in");
        }
        String userEmail = (String) session.getAttribute("email");
        if (userService.deleteUser(userEmail)){
            response.sendRedirect("pages/home.jsp");
        } else {
            logger.info("Delete failed");
        }
    }

    private boolean userLoggedIn(HttpSession session){
        return session != null && session.getAttribute("email") != null;
    }
}

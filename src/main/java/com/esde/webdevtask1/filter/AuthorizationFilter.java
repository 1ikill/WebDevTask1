package com.esde.webdevtask1.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authorizationFilter", urlPatterns = {"/update", "/delete", "/logout", "/user", "/upload",
        "/contacts", "/new-contact", "/update-contact-name", "/update-contact-number", "/delete-contact" , "/pages/user-menu.jsp"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean loggedIn = request.getSession().getAttribute("email") != null;

        if (loggedIn) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}


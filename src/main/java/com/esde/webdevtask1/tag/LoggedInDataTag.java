package com.esde.webdevtask1.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

public class LoggedInDataTag extends TagSupport {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String username = null;
        String email = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if ("userEmail".equals(cookie.getName())){
                    email = cookie.getValue();
                }
            }
        }

        JspWriter out = pageContext.getOut();
        try {
            String message = "Logged in as: " + username + ", " + email;
            out.print(message);
        } catch (IOException e){
            logger.error(e);
        }
        return SKIP_BODY;
    }
}


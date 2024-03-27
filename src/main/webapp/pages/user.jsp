<%@ page import="com.esde.webdevtask1.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.esde.webdevtask1.model.User" %>
<%--<jsp:useBean id="user" class="com.esde.webdevtask1.model.User" />--%>
<% User user = (User) request.getSession().getAttribute("user"); %>

<%--<%@ page import="javax.servlet.http.HttpServletRequest" %>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View User</title>
</head>
<body>
<h2>User Details</h2>
<table >

    <tr>
        <th>Username:</th>
        <td>${user.userName}</td>
    </tr>
    <tr>
        <th>Email:</th>
        <td>${user.email}</td>
    </tr>
</table>

<ul>
    <li><a href="user-menu.jsp">Menu</a></li>
</ul>
</body>
</html>

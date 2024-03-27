<%@ page import="com.esde.webdevtask1.model.Contact" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contacts database</title>
</head>
<body>
<h1>Contacts database</h1>

<c:choose>
    <c:when test="${empty contactsList}">
        <p>No contacts found in the session</p>
    </c:when>
    <c:otherwise>
<%--        <p>Found ${fn:length(contactsList)} contacts in the session</p>--%>

        <table border="1">
            <tr>
                <th>Name</th>
                <th>Number</th>
            </tr>
            <c:forEach items="${contactsList}" var="contact">
                <tr>
                    <td>${contact.name}</td>
                    <td>${contact.number}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<ul>
    <li><a href="user-menu.jsp">Menu</a></li>
</ul>
</body>

<%@ page import="com.esde.webdevtask1.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.esde.webdevtask1.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View User</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="user.h2"/></h2>
<img class="user-image" src="data:image/png;base64,${userImage}" alt="User Image" width="150" height="150">
<table >

    <tr>
        <th><fmt:message key="user.th.username"/></th>
        <td>${user.userName}</td>
    </tr>
    <tr>
        <th><fmt:message key="user.th.email"/></th>
        <td>${user.email}</td>
    </tr>
</table>

<p><a href="pages/user-menu.jsp"><fmt:message key="user.link"/></a></p>
</body>
</html>

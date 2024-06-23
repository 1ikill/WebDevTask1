<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://example.com/tags" prefix="custom" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User menu</title>
</head>
<body>
<custom:LoggedInData/>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="menu.h2"/></h2>
<p><fmt:message key="menu.p"/></p>
<p><a href="${pageContext.request.contextPath}/user"><fmt:message key="menu.user"/></a></p>
<p><a href="update-username.jsp"><fmt:message key="menu.username"/></a></p>
<p><a href="upload.jsp"><fmt:message key="menu.upload"/></a></p>
<p><a href="create-contact.jsp"><fmt:message key="menu.create.contacts"/></a></p>
<p><a href="update-contact-name.jsp"><fmt:message key="menu.update.contactname"/></a></p>
<p><a href="update-contact-number.jsp"><fmt:message key="menu.update.number"/></a></p>
<p><a href="delete-contact.jsp"><fmt:message key="menu.delete.contact"/></a></p>
<p><a href="${pageContext.request.contextPath}/contacts"><fmt:message key="menu.contacts"/></a></p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout"/></a></p>
<p><a href="${pageContext.request.contextPath}/delete"><fmt:message key="menu.delete.account"/></a></p>
</body>
</html>

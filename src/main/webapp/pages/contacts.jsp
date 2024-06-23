<%@ page import="com.esde.webdevtask1.model.Contact" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contacts database</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h1><fmt:message key="contacts.h1"/></h1>

<c:choose>
    <c:when test="${empty contactsList}">
        <p><fmt:message key="contacts.notfound"/></p>
    </c:when>
    <c:otherwise>

        <table border="1">
            <tr>
                <th><fmt:message key="contacts.th.name"/></th>
                <th><fmt:message key="contacts.th.number"/></th>
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

<c:if test="${currentPage != 1}">
    <td><a href="contacts?page=${currentPage - 1}"><fmt:message key="contacts.previous" /></a></td>
</c:if>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${numOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="contacts?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>


<c:if test="${currentPage lt numOfPages}">
    <td><a href="contacts?page=${currentPage + 1}"><fmt:message key="contacts.next" /></a></td>
</c:if>

<p><a href="pages/user-menu.jsp"><fmt:message key="contacts.link"/></a></p>
</body>

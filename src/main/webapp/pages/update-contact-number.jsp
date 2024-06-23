<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <title>Update contact</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="contacts.update.number.h2"/></h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color: green;">${successMessage}</div>
</c:if>
<form action="${pageContext.request.contextPath}/update-contact-number" method="post">
    <label for="name"><fmt:message key="contacts.update.number.name"/></label><br>
    <input type="text" id="name" name="name" required><br>
    <label for="newNumber"><fmt:message key="contacts.update.number.newnumber"/></label><br>
    <input type="text" id="newNumber" name="newNumber" required><br>
    <div>
        <fmt:message key="contacts.update.number.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>
</body>
</html>
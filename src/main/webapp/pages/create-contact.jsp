<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <title>New contact</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="contacts.create.h2"/></h2>
<form action="${pageContext.request.contextPath}/new-contact" method="post">
    <div>
        <label for="name"><fmt:message key="contacts.create.name.label"/></label>
        <input type="text" id="name" name="name" required pattern="\w+" title="name must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="number"><fmt:message key="contacts.create.number.label"/></label>
        <input type="text" id="number" name="number" required>
    </div>
    <div>
        <fmt:message key="contacts.create.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>
</body>
</html>
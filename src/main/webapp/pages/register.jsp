<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <title>Register</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="register.h2"/></h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    <div>
        <label for="username"><fmt:message key="register.username.label"/></label>
        <input type="text" id="username" name="username" required pattern="\w+" title="Username must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="email"><fmt:message key="register.email.label"/></label>
        <input type="email" id="email" name="email" required>
    </div>
    <label for="password"><fmt:message key="register.password.label"/></label>
    <input type="password" id="password" name="password" required pattern=".{8,}" title="Password must be at least 8 characters long.">
    <div>
        <fmt:message key="register.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>

<p><a href="login.jsp"><fmt:message key="register.link"/></a></p>
</body>
</html>
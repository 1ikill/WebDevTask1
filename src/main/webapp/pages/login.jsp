<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <title>Login</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="login.h2"/></h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div>
        <label for="email"><fmt:message key="login.email.label"/></label>
        <input type="email" id="email" name="email" required>
    </div>
    <label for="password"><fmt:message key="login.password.label"/></label>
    <input type="password" id="password" name="password" required>
    <div>
        <fmt:message key="login.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>

<p><a href="register.jsp"><fmt:message key="login.link"/></a></p>
</body>
</html>
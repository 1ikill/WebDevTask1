<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <title>Upload</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h2><fmt:message key="user.upload.h2"/></h2>
<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <div>
        <label for="image"><fmt:message key="user.upload.image.label"/></label>
        <input type="file" id="image" name="image">
        <fmt:message key="user.upload.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
    <div>
        <fmt:message key="user.upload.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>
</body>
</html>

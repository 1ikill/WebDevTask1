<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update contact</title>
</head>
<body>
<h2>Update contact name</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color: green;">${successMessage}</div>
</c:if>
<form action="${pageContext.request.contextPath}/update-contact-name" method="post">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" required><br>
    <label for="newName">New name:</label><br>
    <input type="text" id="newName" name="newName" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>
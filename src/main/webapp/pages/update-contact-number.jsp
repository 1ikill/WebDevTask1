<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update contact</title>
</head>
<body>
<h2>Update contact number</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color: green;">${successMessage}</div>
</c:if>
<form action="${pageContext.request.contextPath}/update-contact-number" method="post">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" required><br>
    <label for="newNumber">New number:</label><br>
    <input type="text" id="newNumber" name="newNumber" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>
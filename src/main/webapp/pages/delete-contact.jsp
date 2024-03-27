<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete contact</title>
</head>
<body>
<h2>Delete contact</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color: green;">${successMessage}</div>
</c:if>
<form action="${pageContext.request.contextPath}/delete-contact" method="post">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>
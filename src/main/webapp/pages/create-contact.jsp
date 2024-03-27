<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New contact</title>
</head>
<body>
<h2>Create contact</h2>
<form action="${pageContext.request.contextPath}/new-contact" method="post">
    <div>
        <label for="name">Contact name:</label>
        <input type="text" id="name" name="name" required pattern="\w+" title="name must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="number">Number:</label>
        <input type="text" id="number" name="number" required>
    </div>
    <div>
        <input type="submit" value="submit">
    </div>
</form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update username</title>
</head>
<body>
<h2>Update username Page</h2>
<form action="${pageContext.request.contextPath}/update" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required pattern="\w+" title="Username must only contain letters, numbers and underscores.">
    </div>
    <div>
        <input type="submit" value="submit">
    </div>
</form>
</body>
</html>

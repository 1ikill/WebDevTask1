<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Register Page</h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required pattern="\w+" title="Username must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required pattern=".{8,}" title="Password must be at least 8 characters long.">
    <div>
        <input type="submit" value="submit">
    </div>
</form>

<ul>
    <li><a href="http://localhost:8080/WebDevTask1_war_exploded/login">Log In</a></li>
</ul>
</body>
</html>
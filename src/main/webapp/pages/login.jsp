<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login Page</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <div>
        <input type="submit" value="submit">
    </div>
</form>

<ul>
    <li><a href="http://localhost:8080/WebDevTask1_war_exploded/register">Sign Up</a></li>
</ul>
</body>
</html>
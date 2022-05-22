<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>User, id = ${user.id}</h1>
    <div>${user.firstName} ${user.lastName}, ${user.roleDto.toString().toLowerCase()}</div>
    <div>Email = ${user.email}</div>
    <div>Password = ${user.password}</div>
</body>
</html>
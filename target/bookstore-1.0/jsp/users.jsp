<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Users</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Password</th>
            <th>Role</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td><a href="controller?command=user&id=${user.id}" target="blank">${user.lastName}</a></td>
                <td>${user.email}</td>
                <td>${user.password}</td>
                <td>${user.roleDto.toString().toLowerCase()}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
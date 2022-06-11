<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="../../css/style.css">
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
            <th colspan="3">Action</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><c:out value="${user.firstName}"/></td>
                <td><a href="/users/${user.id}" target="_blank"><c:out value="${user.lastName}"/></a></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td>${user.roleDto.toString().toLowerCase()}</td>
                <td id="method"><form action="/users/${user.id}"><input id="crud" type="submit" value="View"/></form>
                                <form action="/users/update/${user.id}"><input id="crud" type="submit" value="Update"/></form>
                                <form action="/users/delete/${user.id}"><input id="crud" type="submit" value="Delete"/></form></td>
            </tr>
        </c:forEach>
    </table>
    <form action="/users/create"><input id="crud" type="submit" value="Create"/></form>
    <form action="/"><input id="crud" type="submit" value="To main"/></form>
</body>
</html>
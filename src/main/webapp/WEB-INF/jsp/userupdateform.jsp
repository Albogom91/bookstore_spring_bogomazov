<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html xmlns:th="http://www.thymeleaf.org">
<html>
<head>
    <title>User Form</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<table>
     <form action="/users/update/${user.id}" method="POST">
     <input name="id" type="hidden" value="${user.id}"/>
        <tr>
            <td>First name</td>
            <td><input name="firstName" type="text" value="${user.firstName}"/></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><input name="lastName" type="text" value="${user.lastName}"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input name="email" type="text" value="${user.email}"/></td>
        </tr>
        <tr>
             <td>Password</td>
             <td><input name="password" type="text" value="${user.password}"/></td>
        </tr>
        <tr>
            <td>Role</td>
            <td><input type="radio" name="roleDto" value="ADMIN" checked/>Admin
                <input type="radio" name="roleDto" value="MANAGER"/>Manager
                <input type="radio" name="roleDto" value="CUSTOMER"/>Customer</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Update"/></td>
        </tr>
        </form>
    </table>
</body>
</html>
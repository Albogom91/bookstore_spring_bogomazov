<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Form</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<table>
     <form action="create" method="POST">
        <tr>
            <td>First name</td>
            <td><input name="firstName"/></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><input name="lastName"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input name="email"/></td>
        </tr>
        <tr>
             <td>Password</td>
             <td><input name="password"/></td>
        </tr>
        <tr>
            <td>Role</td>
            <td><input type="radio" name="roleDto" value="ADMIN" checked/>Admin
                <input type="radio" name="roleDto" value="MANAGER"/>Manager
                <input type="radio" name="roleDto" value="CUSTOMER"/>Customer</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit" /></td>
        </tr>
        </form>
    </table>
</body>
</html>
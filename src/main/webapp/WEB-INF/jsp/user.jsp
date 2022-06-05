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
    <div>
        <form action="/users?page=0&size=20&sort=asc&column=id">
            <input type="hidden" name="page" value="0"/>
            <input type="hidden" name="size" value="10"/>
            <input type="hidden" name="sort" value="asc"/>
            <input type="hidden" name="column" value="id"/>
            <input id="crud" type="submit" value="To users"/>
        </form>
    </div>
</body>
</html>
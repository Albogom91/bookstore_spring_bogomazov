<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <div>User with id = ${id} was deleted!</div>
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


<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bookstore</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Welcome to the bookstore!</h1>
    <div>Please, choose what information you would like to access:</div>
    <ul>
      <li><a href="/books?page=0&size=20&sort=asc&column=id" target="_blank">Books</a></li>
      <li><a href="/users?page=0&size=20&sort=asc&column=id" target="_blank">Users</a></li>
      <li><a href="/orders?page=0&size=20&sort=asc&column=id" target="_blank">Orders</a></li>
    </ul>
</body>
</html>
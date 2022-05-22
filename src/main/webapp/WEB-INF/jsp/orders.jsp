<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Orders</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>User ID</th>
            <th>Total cost</th>
            <th>Time</th>
            <th>Status</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><a href="orders/${order.id}" target="blank">${order.id}</a></td>
                <td><a href="users/${order.userDto.getId()}" target="blank">${order.userDto.getId()}</a></td>
                <td>${order.totalCost}</td>
                <td>${order.timestamp.toString()}</td>
                <td>${order.statusDto.toString().toLowerCase()}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
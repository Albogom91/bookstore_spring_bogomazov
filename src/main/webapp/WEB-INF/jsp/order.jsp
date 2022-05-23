<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Order, id = ${order.id}</h1>
    <div>Order total cost: ${order.totalCost}, status: ${order.statusDto.toString().toLowerCase()}</div>
    <div>Order timestamp: ${order.timestamp.toString()}</div>
    <div>Order includes following items:</div>
    <hr>
    <table>
        <tr>
            <th>Order item id</th>
            <th>Book ID</th>
            <th>Book quantity</th>
            <th>Book price</th>
        </tr>
        <c:forEach items="${order.items}" var="item">
            <tr>
                <td>${item.id}</td>
                <td><a href="../books/${item.bookDto.getId()}" target="_blank">${item.bookDto.getId()}</a></td>
                <td>${item.quantity}</td>
                <td>${item.price}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
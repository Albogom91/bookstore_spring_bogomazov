<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Order, id = ${order.id}</h1>
    <div>${order.totalCost}, status: ${order.statusDto.toString().toLowerCase()}</div>
    <div>${order.timestamp.toString()}</div>
    <div>Order includes following items:</div>
    <c:forEach items="${order.items}" var="item">
    <div>
        <div>Order item id: ${item.id}</div>
        <div><a href="../books/${item.bookDto.getId()}" target="blank">Book id: ${item.bookDto.getId()}</a></div>
        <div>Book quantity: ${item.quantity}</div>
        <div>Book price: ${item.price}</div>
    </div>
    </c:forEach>
</body>
</html>
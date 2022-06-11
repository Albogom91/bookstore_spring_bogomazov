<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Books</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>ISBN</th>
            <th>Title</th>
            <th>Author</th>
            <th>Price</th>
            <th>Cover</th>
            <th colspan="3">Action</th>
        </tr>
        <c:forEach items="${books}" var="book">
            <tr>
                <td>${book.id}</td>
                <td><c:out value="${book.isbn}"/></td>
                <td><a href="/books/${book.id}" target="_blank"><c:out value="${book.title}"/></a></td>
                <td><c:out value="${book.author}"/></td>
                <td>${book.price}</td>
                <td>${book.coverDto.toString().toLowerCase()}</td>
                <td id="method"><form action="/books/${book.id}"><input id="crud" type="submit" value="View"/></form>
                                <form action="/books/update/${book.id}"><input id="crud" type="submit" value="Update"/></form>
                                <form action="/books/delete/${book.id}"><input id="crud" type="submit" value="Delete"/></form></td>
            </tr>
        </c:forEach>
    </table>
    <form action="/books/create"><input id="crud" type="submit" value="Create"/></form>
    <form action="/"><input id="crud" type="submit" value="To main"/></form>
</body>
</html>
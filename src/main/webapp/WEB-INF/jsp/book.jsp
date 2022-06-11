<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <h1>Book, id = ${book.id}</h1>
    <div><c:out value="${book.title}"/> by <c:out value="${book.author}"/> in ${book.coverDto.toString().toLowerCase()} cover</div>
    <div>ISBN = <c:out value="${book.isbn}"/></div>
    <div>Price = ${book.price}</div>
    <div>
        <form action="/books?page=0&size=20&sort=asc&column=id">
            <input type="hidden" name="page" value="0"/>
            <input type="hidden" name="size" value="10"/>
            <input type="hidden" name="sort" value="asc"/>
            <input type="hidden" name="column" value="id"/>
            <input id="crud" type="submit" value="To books"/>
        </form>
    </div>
</body>
</html>
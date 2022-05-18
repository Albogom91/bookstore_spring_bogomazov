<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Book, id = ${book.id}</h1>
    <div>${book.title} by ${book.author} in ${book.coverDto.toString().toLowerCase()} cover</div>
    <div>ISBN = ${book.isbn}</div>
    <div>Price = ${book.price}</div>
</body>
</html>
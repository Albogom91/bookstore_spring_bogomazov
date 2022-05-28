<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Form</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<table>
     <form action="/books/create" method="POST">
        <tr>
            <td>ISBN</td>
            <td><input name="isbn" type="text"/></td>
        </tr>
        <tr>
            <td>Title</td>
            <td><input name="title" type="text"/></td>
        </tr>
        <tr>
            <td>Author</td>
            <td><input name="author" type="text"/></td>
        </tr>
        <tr>
            <td>Cover</td>
            <td><input type="radio" name="coverDto" value="SOFT" checked/>Soft
                <input type="radio" name="coverDto" value="HARD"/>Hard
                <input type="radio" name="coverDto" value="SPECIAL"/>Special</td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input name="price" type="number" step=".01"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Create"/></td>
        </tr>
        </form>
    </table>
</body>
</html>